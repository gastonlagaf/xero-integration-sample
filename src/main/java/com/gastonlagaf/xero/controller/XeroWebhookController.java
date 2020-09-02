package com.gastonlagaf.xero.controller;

import com.gastonlagaf.xero.controller.model.webhook.XeroEventDto;
import com.gastonlagaf.xero.controller.model.webhook.XeroWebhookDto;
import com.gastonlagaf.xero.service.AccountingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/webhook/xero")
public class XeroWebhookController {

    private final AccountingService accountingService;

    @PostMapping
    public void processWebhook(@RequestBody XeroWebhookDto payload) {
        Optional.ofNullable(payload.getEvents())
                .stream()
                .flatMap(List::stream)
                .map(XeroEventDto::toEntity)
                .forEach(accountingService::processXeroEvent);
    }

}
