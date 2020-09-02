package com.gastonlagaf.xero.entity;

import com.gastonlagaf.xero.entity.base.AuditableEntity;
import com.gastonlagaf.xero.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xero_tokens")
public class XeroToken extends AuditableEntity {

    @Transient
    private String accessToken;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "expiry", nullable = false)
    private Integer expiry;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

}
