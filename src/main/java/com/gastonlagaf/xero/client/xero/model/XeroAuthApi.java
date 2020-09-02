package com.gastonlagaf.xero.client.xero.model;

import com.github.scribejava.core.builder.api.DefaultApi20;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class XeroAuthApi extends DefaultApi20 {

    private String accessTokenEndpoint;

    private String authorizationBaseUrl;

}
