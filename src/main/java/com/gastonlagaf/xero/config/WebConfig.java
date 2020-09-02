package com.gastonlagaf.xero.config;

import com.gastonlagaf.xero.controller.filter.XeroWebhookFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class WebConfig {

    private static final String XERO_WEBHOOK_URI = "/api/webhook/xero";

    private final AccountingProperties accountingProperties;

    @Bean
    public FilterRegistrationBean<XeroWebhookFilter> xeroWebhookFilter() {
        FilterRegistrationBean<XeroWebhookFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XeroWebhookFilter(accountingProperties.getWebhookKey()));
        registrationBean.addUrlPatterns(XERO_WEBHOOK_URI);
        return registrationBean;
    }

}
