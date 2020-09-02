package com.gastonlagaf.xero.repository;

import com.gastonlagaf.xero.entity.Invoice;

import java.util.Optional;

public interface InvoiceRepository extends BaseRepository<Invoice> {

    Optional<Invoice> findByExternalId(String value);

}
