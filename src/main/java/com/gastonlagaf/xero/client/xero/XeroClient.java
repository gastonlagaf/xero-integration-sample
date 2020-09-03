package com.gastonlagaf.xero.client.xero;

import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.entity.InvoiceType;
import com.gastonlagaf.xero.entity.XeroToken;

import java.math.BigDecimal;

public interface XeroClient {

    String getAuthorizationUrl();

    String createInvoice(AccountingContact accountingContact, InvoiceType type, BigDecimal fee);

    String createContact(String email);

    BigDecimal getInvoiceTotal(String invoiceId);

    String addPayment(String invoiceId);

    XeroToken authorize(String code);

    XeroToken refreshToken();

}
