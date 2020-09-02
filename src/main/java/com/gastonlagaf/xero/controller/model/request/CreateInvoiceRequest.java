package com.gastonlagaf.xero.controller.model.request;

import com.gastonlagaf.xero.entity.InvoiceType;
import lombok.Data;

@Data
public class CreateInvoiceRequest {

    private String email;

    private InvoiceType type;

}
