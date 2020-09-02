package com.gastonlagaf.xero.exception;

public class XeroTokenNotFoundException extends RuntimeException {

    public XeroTokenNotFoundException() {
        super("Xero token information is not provided");
    }

}
