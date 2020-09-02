package com.gastonlagaf.xero.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("accounting.xero")
public class AccountingProperties {

    private String clientId;

    private String clientSecret;

    private String accessTokenEndpoint;

    private String authorizationBaseUrl;

    private String callbackUrl;

    private String scope;

    private String webhookKey;

    private String responseType = "code";

}
