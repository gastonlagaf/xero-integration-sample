package com.gastonlagaf.xero.model;

import lombok.Data;
import lombok.Value;

import java.time.LocalDateTime;

@Data
public class XeroEvent {

    private final String resourceUrl;

    private final String resourceId;

    private final LocalDateTime eventDateTime;

    private final XeroEventType eventType;

    private final XeroEventCategory eventCategory;

    private final String tenantId;

    private final String tenantType;

}
