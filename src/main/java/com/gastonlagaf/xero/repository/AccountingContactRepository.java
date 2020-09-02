package com.gastonlagaf.xero.repository;

import com.gastonlagaf.xero.entity.AccountingContact;

import java.util.Optional;

public interface AccountingContactRepository extends BaseRepository<AccountingContact> {

    Optional<AccountingContact> findByEmail(String value);

}
