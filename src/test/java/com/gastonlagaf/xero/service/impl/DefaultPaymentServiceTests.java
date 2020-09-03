package com.gastonlagaf.xero.service.impl;

import com.gastonlagaf.xero.client.stripe.DefaultStripeClient;
import com.gastonlagaf.xero.client.stripe.StripeClient;
import com.gastonlagaf.xero.service.PaymentService;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultPaymentServiceTests {

    private final StripeClient stripeClient = new DefaultStripeClient("");
    private final PaymentService paymentService = new DefaultPaymentService(stripeClient);

    @Test
    public void calculateFeesTest() {
        BigDecimal correctUsFee = new BigDecimal("0.47");
        BigDecimal correctEuFee = new BigDecimal("0.36");

        BigDecimal usFee = paymentService.calculateFees(BigDecimal.valueOf(7), "US");
        assertEquals(usFee, correctUsFee);

        BigDecimal euFee = paymentService.calculateFees(BigDecimal.valueOf(7), "AD");
        assertEquals(euFee, correctEuFee);
    }

}