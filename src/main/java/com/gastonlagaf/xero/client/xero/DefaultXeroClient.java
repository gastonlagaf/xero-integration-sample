package com.gastonlagaf.xero.client.xero;

import com.gastonlagaf.xero.client.xero.model.XeroAuthApi;
import com.gastonlagaf.xero.config.AccountingProperties;
import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.entity.InvoiceType;
import com.gastonlagaf.xero.entity.XeroToken;
import com.gastonlagaf.xero.model.XeroAuthCredentials;
import com.gastonlagaf.xero.service.XeroTokenService;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth2AccessToken;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.xero.api.ApiClient;
import com.xero.api.client.AccountingApi;
import com.xero.api.client.IdentityApi;
import com.xero.models.accounting.*;
import com.xero.models.identity.Connection;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.threeten.bp.LocalDate;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class DefaultXeroClient implements XeroClient {

    private static final String ACCOUNT_ID = "562555F2-8CDE-4CE9-8203-0363922537A4";

    private static final Integer INVOICE_DUE_DATE_OFFSET = 1;

    private static final String IDENTITY_API_URL = "https://api.xero.com";

    private final AccountingProperties properties;

    private final XeroTokenService xeroTokenService;
    private final OAuth20Service authService;

    private final IdentityApi identityApi;
    private final AccountingApi accountingApi;

    @SneakyThrows
    public DefaultXeroClient(AccountingProperties properties, XeroTokenService xeroTokenService) {
        this.properties = properties;
        this.xeroTokenService = xeroTokenService;

        XeroAuthApi xeroAuthApi = new XeroAuthApi(
                properties.getAccessTokenEndpoint(),
                properties.getAuthorizationBaseUrl()
        );
        this.authService = new ServiceBuilder(properties.getClientId())
                .apiSecret(properties.getClientSecret()).defaultScope(properties.getScope())
                .callback(properties.getCallbackUrl()).responseType(properties.getResponseType()).build(xeroAuthApi);

        ApiClient xeroApiAccounting = new ApiClient();
        ApiClient xeroApiIdentity = new ApiClient(IDENTITY_API_URL, null, null, null, null);

        this.identityApi = new IdentityApi(xeroApiIdentity);
        this.accountingApi = new AccountingApi(xeroApiAccounting);
    }

    @Override
    public String getAuthorizationUrl() {
        return authService.getAuthorizationUrl();
    }

    @SneakyThrows
    @Override
    public XeroToken authorize(String code) {
        OAuth2AccessToken token = authService.getAccessToken(code);
        String tenantId = getTenantId(token.getAccessToken());
        XeroToken xeroToken = new XeroToken(token.getAccessToken(), token.getRefreshToken(), token.getExpiresIn(), tenantId);
        XeroToken persistedToken = xeroTokenService.save(xeroToken);
        log.info("Got tenant id " + tenantId);
        return persistedToken;
    }

    @SneakyThrows
    @Override
    public String createInvoice(AccountingContact accountingContact, InvoiceType type) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        Contact contact = getContact(accountingContact.getExternalId());

        LineItem item = new LineItem()
                .description(type.name()).unitAmount(type.getAmount().doubleValue()).taxType("INPUT").accountCode("200");
        Invoice invoice = new Invoice()
                .sentToContact(true).contact(contact).addLineItemsItem(item)
                .status(Invoice.StatusEnum.AUTHORISED).type(Invoice.TypeEnum.ACCREC);
        invoice.setDueDate(LocalDate.now().plusMonths(INVOICE_DUE_DATE_OFFSET));
        Invoices invoiceList = new Invoices().addInvoicesItem(invoice);

        Invoice newInvoice = accountingApi.createInvoices(token.getAccessToken(), token.getTenantId(), invoiceList, true, null)
                .getInvoices().stream().findFirst().orElseThrow();
        emailInvoice(newInvoice);
        return newInvoice.getInvoiceID().toString();
    }

    @SneakyThrows
    public String addPayment(String invoiceId) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        Invoice invoice = getInvoice(invoiceId);
        Account account = getAccount(ACCOUNT_ID);
        Payment payment = new Payment().account(account).invoice(invoice).amount(invoice.getTotal());
        payment.setDate(LocalDate.now());
        return accountingApi.createPayment(token.getAccessToken(), token.getTenantId(), payment)
                .getPayments().stream().findFirst().orElseThrow().getPaymentID().toString();
    }

    @SneakyThrows
    private Invoice getInvoice(String externalId) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        return accountingApi.getInvoice(token.getAccessToken(), token.getTenantId(), UUID.fromString(externalId), null)
                .getInvoices().stream().findFirst().orElseThrow();
    }

    @SneakyThrows
    private Account getAccount(String externalId) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        return accountingApi.getAccount(token.getAccessToken(), token.getTenantId(), UUID.fromString(externalId))
                .getAccounts().stream().findFirst().orElseThrow();
    }

    @Override
    @SneakyThrows
    public String createContact(String email) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        Contact contact = new Contact()
                .name(email)
                .emailAddress(email);
        Contacts contactList = new Contacts().addContactsItem(contact);
        return accountingApi.createContacts(token.getAccessToken(), token.getTenantId(), contactList, true)
                .getContacts().stream().findFirst().orElseThrow().getContactID().toString();
    }

    @Override
    @SneakyThrows
    public XeroToken refreshToken() {
        XeroToken existingToken = xeroTokenService.get();
        OAuth2AccessToken token = authService.refreshAccessToken(existingToken.getRefreshToken(), properties.getScope());
        XeroToken xeroToken = new XeroToken(token.getAccessToken(), token.getRefreshToken(), token.getExpiresIn(), existingToken.getTenantId());
        return xeroTokenService.save(xeroToken);
    }

    @SneakyThrows
    private void emailInvoice(Invoice invoice) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        accountingApi.emailInvoice(token.getAccessToken(), token.getTenantId(), invoice.getInvoiceID(), new RequestEmpty());
    }

    @SneakyThrows
    private Contact getContact(String contactId) {
        XeroAuthCredentials token = xeroTokenService.getCredentials();
        return accountingApi.getContact(token.getAccessToken(), token.getTenantId(), UUID.fromString(contactId))
                .getContacts().stream().findFirst().orElseThrow();
    }

    @SneakyThrows
    private String getTenantId(String accessToken) {
        List<Connection> connections = identityApi.getConnections(accessToken, null);
        return connections.stream().findFirst().orElseThrow().getTenantId().toString();
    }

}
