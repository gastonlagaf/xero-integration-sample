package com.gastonlagaf.xero.client.xero;

import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.entity.InvoiceType;
import com.gastonlagaf.xero.entity.XeroToken;

public interface XeroClient {

    String getAuthorizationUrl();

    String createInvoice(AccountingContact accountingContact, InvoiceType type);

    String createContact(String email);

    String addPayment(String invoiceId);

    XeroToken authorize(String code);

    XeroToken refreshToken();

}
