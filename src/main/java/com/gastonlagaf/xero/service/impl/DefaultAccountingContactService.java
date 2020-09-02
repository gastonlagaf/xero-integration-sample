package com.gastonlagaf.xero.service.impl;

import com.gastonlagaf.xero.entity.AccountingContact;
import com.gastonlagaf.xero.repository.AccountingContactRepository;
import com.gastonlagaf.xero.service.AccountingContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultAccountingContactService implements AccountingContactService {

    private final AccountingContactRepository repository;

    @Override
    public AccountingContact getByEmail(String value) {
        return repository.findByEmail(value).orElseThrow();
    }

    @Override
    public AccountingContact save(AccountingContact accountingContact) {
        return repository.save(accountingContact);
    }
}
