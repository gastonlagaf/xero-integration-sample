package com.gastonlagaf.xero.service.impl;

import com.gastonlagaf.xero.client.stripe.util.EuCountryList;
import com.gastonlagaf.xero.client.stripe.StripeClient;
import com.gastonlagaf.xero.service.PaymentService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultPaymentService implements PaymentService {

    private static final Fee EU_FEE = new Fee(new BigDecimal("0.014"), new BigDecimal("0.25"));
    private static final Fee NON_EU_FEE = new Fee(new BigDecimal("0.029"), new BigDecimal("0.25"));

    private final StripeClient stripeClient;

    @Override
    public BigDecimal calculateFees(BigDecimal originalAmount, String countryCode) {
        Fee fee = EuCountryList.contains(countryCode) ? EU_FEE : NON_EU_FEE;
        BigDecimal numerator = originalAmount.add(fee.fixedAmount);
        BigDecimal denominator = BigDecimal.ONE.subtract(fee.percent);
        return numerator.divide(denominator, RoundingMode.CEILING)
                .subtract(originalAmount)
                .setScale(2, RoundingMode.CEILING);
    }

    @Override
    public void executePayment(BigDecimal amount, String paymentToken) {
        stripeClient.executePayment(amount, paymentToken);
    }

    @Data
    private static class Fee {

        private final BigDecimal percent;

        private final BigDecimal fixedAmount;

    }
}
