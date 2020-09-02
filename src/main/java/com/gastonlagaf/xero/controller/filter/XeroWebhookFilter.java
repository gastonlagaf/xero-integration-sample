package com.gastonlagaf.xero.controller.filter;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class XeroWebhookFilter implements Filter {

    private static final String ALGO_NAME = "HmacSHA256";
    private static final String SIGNATURE_HEADER_NAME = "x-xero-signature";

    private final String key;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String signature = httpRequest.getHeader(SIGNATURE_HEADER_NAME);
        if (null == signature) {
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        String hashingResult = generateHash(requestWrapper.getContentAsByteArray());

        if (!signature.equals(hashingResult)) {
            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        chain.doFilter(requestWrapper, response);
    }

    @SneakyThrows
    private String generateHash(byte[] payload) {
        Mac hmacSha256 = Mac.getInstance(ALGO_NAME);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), ALGO_NAME);
        hmacSha256.init(keySpec);
        byte[] signatureBytes = hmacSha256.doFinal(payload);
        return Base64.encodeBase64String(signatureBytes);
    }
}
