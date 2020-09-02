package com.gastonlagaf.xero.entity;

import com.gastonlagaf.xero.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounting_contacts")
public class AccountingContact extends BaseEntity {

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(name = "email", nullable = false)
    private String email;

}
