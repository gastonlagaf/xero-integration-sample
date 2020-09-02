package com.gastonlagaf.xero.controller.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gastonlagaf.xero.controller.model.base.TransferObject;
import com.gastonlagaf.xero.model.XeroEvent;
import com.gastonlagaf.xero.model.XeroEventCategory;
import com.gastonlagaf.xero.model.XeroEventType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class XeroEventDto implements TransferObject<XeroEvent> {

    private String resourceUrl;

    private String resourceId;

    private LocalDateTime eventDateTime;

    private XeroEventType eventType;

    private XeroEventCategory eventCategory;

    private String tenantId;

    private String tenantType;

    @Override
    @JsonIgnore
    public XeroEvent toEntity() {
        return new XeroEvent(
                this.resourceUrl,
                this.resourceId,
                this.eventDateTime,
                this.eventType,
                this.eventCategory,
                this.tenantId,
                this.tenantType
        );
    }
}
