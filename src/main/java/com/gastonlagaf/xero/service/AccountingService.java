package com.gastonlagaf.xero.service;

import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.entity.Invoice;
import com.gastonlagaf.xero.entity.InvoiceType;
import com.gastonlagaf.xero.model.XeroEvent;

public interface AccountingService {

    Invoice createInvoice(String recipientEmail, InvoiceType type);

    Invoice getInvoice(String externalId);

    Invoice payInvoice(Long invoiceId);

    AccountingContact createContact(String email);

    void processXeroEvent(XeroEvent event);

}
