package com.gastonlagaf.xero.controller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gastonlagaf.xero.controller.model.base.TransferObject;
import com.gastonlagaf.xero.entity.Invoice;
import com.gastonlagaf.xero.entity.InvoiceType;
import lombok.Data;

@Data
public class InvoiceDto implements TransferObject<Invoice> {

    private Long id;

    private String externalId;

    private InvoiceType type;

    private Boolean paid;

    public InvoiceDto(Invoice invoice) {
        this.id = invoice.getId();
        this.externalId = invoice.getExternalId();
        this.type = invoice.getType();
        this.paid = invoice.getPaid();
    }

    @Override
    @JsonIgnore
    public Invoice toEntity() {
        return new Invoice(
                externalId,
                type,
                paid
        ).setId(id);
    }
}
