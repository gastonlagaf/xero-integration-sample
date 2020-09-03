package com.gastonlagaf.xero.client.stripe;

import java.math.BigDecimal;

public interface StripeClient {

    void executePayment(BigDecimal amount, String paymentToken);

    String getTokenCountry(String paymentToken);

}
