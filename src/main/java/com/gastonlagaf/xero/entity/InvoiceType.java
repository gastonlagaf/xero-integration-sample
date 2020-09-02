package com.gastonlagaf.xero.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
public enum InvoiceType {

    ACCREDITATION(new BigDecimal("10")),
    EVENT(new BigDecimal("7"));

    @Getter
    private BigDecimal amount;

}
