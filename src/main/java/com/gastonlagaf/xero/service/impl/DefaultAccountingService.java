package com.gastonlagaf.xero.service.impl;

import com.gastonlagaf.xero.client.xero.XeroClient;
import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.entity.Invoice;
import com.gastonlagaf.xero.entity.InvoiceType;
import com.gastonlagaf.xero.model.XeroEvent;
import com.gastonlagaf.xero.model.XeroEventCategory;
import com.gastonlagaf.xero.model.XeroEventType;
import com.gastonlagaf.xero.repository.InvoiceRepository;
import com.gastonlagaf.xero.service.AccountingContactService;
import com.gastonlagaf.xero.service.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DefaultAccountingService implements AccountingService {

    private final AccountingContactService accountingContactService;
    private final InvoiceRepository repository;
    private final XeroClient xeroClient;

    @Override
    @Transactional
    public Invoice createInvoice(String recipientEmail, InvoiceType type) {
        AccountingContact accountingContact = accountingContactService.getByEmail(recipientEmail);
        String externalId = xeroClient.createInvoice(accountingContact, type);
        Invoice invoice = new Invoice(externalId, type, false);
        return repository.save(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getInvoice(String externalId) {
        return repository.findByExternalId(externalId).orElseThrow();
    }

    @Override
    @Transactional()
    public Invoice payInvoice(Long id) {
        Invoice invoice = repository.findById(id).orElseThrow();
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
