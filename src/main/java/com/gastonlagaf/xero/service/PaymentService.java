package com.gastonlagaf.xero.service;

import java.math.BigDecimal;

public interface PaymentService {

    BigDecimal calculateFees(BigDecimal originalAmount, String countryCode);

    void executePayment(BigDecimal amount, String paymentToken);

}
