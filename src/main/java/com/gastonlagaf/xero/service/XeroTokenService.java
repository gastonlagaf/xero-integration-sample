package com.gastonlagaf.xero.service;

import com.gastonlagaf.xero.entity.XeroToken;
import com.gastonlagaf.xero.model.XeroAuthCredentials;

public interface XeroTokenService {

    XeroToken save(XeroToken xeroToken);

    XeroToken get();

    XeroAuthCredentials getCredentials();

}
