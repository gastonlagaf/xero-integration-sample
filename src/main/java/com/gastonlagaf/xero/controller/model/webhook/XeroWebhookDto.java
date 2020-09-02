package com.gastonlagaf.xero.controller.model.webhook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gastonlagaf.xero.controller.model.base.TransferObject;
import com.gastonlagaf.xero.model.XeroWebhook;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class XeroWebhookDto implements TransferObject<XeroWebhook> {

    private Long lastEventSequence;

    private Long firstEventSequence;

    private String entropy;

    private List<XeroEventDto> events;

    @Override
    @JsonIgnore
    public XeroWebhook toEntity() {
        return new XeroWebhook(
                this.lastEventSequence,
                this.firstEventSequence,
                this.entropy,
                this.events.stream().map(XeroEventDto::toEntity).collect(Collectors.toList())
        );
    }
}
