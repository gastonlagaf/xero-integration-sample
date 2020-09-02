package com.gastonlagaf.xero.model;

import lombok.Data;

import java.util.List;

@Data
public class XeroWebhook {

    private final Long lastEventSequence;

    private final Long firstEventSequence;

    private final String entropy;

    private final List<XeroEvent> events;

}
