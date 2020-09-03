package com.gastonlagaf.xero.service.impl;

import com.gastonlagaf.xero.client.xero.XeroClient;
import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.entity.Invoice;
import com.gastonlagaf.xero.entity.InvoiceType;
import com.gastonlagaf.xero.exception.PaymentException;
import com.gastonlagaf.xero.model.XeroEvent;
import com.gastonlagaf.xero.model.XeroEventCategory;
import com.gastonlagaf.xero.model.XeroEventType;
import com.gastonlagaf.xero.repository.InvoiceRepository;
import com.gastonlagaf.xero.service.AccountingContactService;
import com.gastonlagaf.xero.service.AccountingService;
import com.gastonlagaf.xero.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class DefaultAccountingService implements AccountingService {

    private final AccountingContactService accountingContactService;
    private final PaymentService paymentService;
    private final InvoiceRepository repository;
    private final XeroClient xeroClient;

    @Override
    @Transactional
    public Invoice createInvoice(String recipientEmail, InvoiceType type, String countryCode) {
        AccountingContact accountingContact = accountingContactService.getByEmail(recipientEmail);
        BigDecimal fee = paymentService.calculateFees(type.getAmount(), countryCode);
        String externalId = xeroClient.createInvoice(accountingContact, type, fee);
        Invoice invoice = new Invoice(externalId, type, false);
        return repository.save(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getInvoice(String externalId) {
        return repository.findByExternalId(externalId).orElseThrow();
    }

    @Override
    @Transactional
    public Invoice payInvoice(Long id, String paymentToken) {
        Invoice invoice = repository.findById(id).orElseThrow();
        if (invoice.getPaid()) {
            throw new PaymentException("Invoice already paid");
        }
        BigDecimal amount = xeroClient.getInvoiceTotal(invoice.getExternalId());
        paymentService.executePayment(amount, paymentToken);
        xeroClient.addPayment(invoice.getExternalId());
        invoice.setPaid(true);
        return repository.save(invoice);
    }

    @Override
    @Transactional
    public AccountingContact createContact(String email) {
        String externalId = xeroClient.createContact(email);
        AccountingContact accountingContact = new AccountingContact(externalId, email);
        return accountingContactService.save(accountingContact);
    }

    @Override
    public void processXeroEvent(XeroEvent event) {
        if (XeroEventCategory.CONTACT == event.getEventCategory()) {
            log.warn("Inbound webhook with contact-related changes");
        } else if (XeroEventType.UPDATE == event.getEventType()) {
//            xeroClient.getInvoice(event.getResourceId());
        }
    }
}
