package com.gastonlagaf.xero.controller;

import com.gastonlagaf.xero.client.xero.XeroClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/xero")
public class XeroAuthorizationController {

    private final XeroClient xeroClient;

    @GetMapping("/authorize/init")
    public RedirectView initAuthorization() {
        String url = xeroClient.getAuthorizationUrl();
        log.info(url);
        return new RedirectView(url);
    }

    @GetMapping("/authorize")
    public void authorize(@RequestParam String code, @RequestParam(required = false) String error) {
        if (null != error) {
            throw new IllegalArgumentException(error);
        }
        xeroClient.authorize(code);
    }

}
