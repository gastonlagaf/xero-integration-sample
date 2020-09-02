package com.gastonlagaf.xero.entity;

import com.gastonlagaf.xero.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Enumerated
    @Column(name = "type", nullable = false)
    private InvoiceType type;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

}
