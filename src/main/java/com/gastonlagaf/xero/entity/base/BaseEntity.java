package com.gastonlagaf.xero.entity.base;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public abstract class BaseEntity implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @Override
    public <T extends Identifiable> T setId(Long id) {
        this.id = id;
        return (T)this;
    }
}
