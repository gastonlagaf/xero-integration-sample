package com.gastonlagaf.xero.model;

import lombok.Data;

@Data
public class XeroAuthCredentials {

    private final String accessToken;

    private final String tenantId;

}
