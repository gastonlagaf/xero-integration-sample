package com.gastonlagaf.xero.entity.base;

public interface Identifiable {

    Long getId();

    <T extends Identifiable> T setId(Long value);

}
