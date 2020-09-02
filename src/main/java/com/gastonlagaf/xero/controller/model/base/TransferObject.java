package com.gastonlagaf.xero.controller.model.base;

import com.gastonlagaf.xero.entity.base.BaseEntity;

public interface TransferObject<T> {

    T toEntity();

}
