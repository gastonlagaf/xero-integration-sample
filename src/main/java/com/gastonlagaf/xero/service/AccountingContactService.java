package com.gastonlagaf.xero.service;

import com.gastonlagaf.xero.entity.AccountingContact;

public interface AccountingContactService {

    AccountingContact getByEmail(String value);

    AccountingContact save(AccountingContact accountingContact);

}
