package com.gastonlagaf.xero.client.stripe;

import com.gastonlagaf.xero.client.stripe.util.ChargeErrorMapping;
import com.gastonlagaf.xero.exception.PaymentException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import com.stripe.param.ChargeCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class DefaultStripeClient implements StripeClient {;

    private static final String USD_CURRENCY = "EUR";
    private static final String CARD_TOKEN_TYPE = "card";

    private static final BigDecimal CENTS_IN_DOLLAR = BigDecimal.valueOf(100);

    public DefaultStripeClient(@Value("${payment.stripe.key}") String apiKey) {
        Stripe.apiKey = apiKey;
    }

    @Override
    public void executePayment(BigDecimal amount, String paymentToken) {
        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount(amount.multiply(CENTS_IN_DOLLAR).longValueExact())
                .setSource(paymentToken)
                .setCurrency(USD_CURRENCY)
                .build();
        try {
            Charge.create(params);
        } catch (StripeException ex) {
            log.error(ex.getCode());
            throw new PaymentException(ChargeErrorMapping.get(ex.getStripeError().getDeclineCode()));
        }
    }

    @Override
    public String getTokenCountry(String paymentToken) {
        String result;
        try {
            Token token = Token.retrieve(paymentToken);
            if (CARD_TOKEN_TYPE.equals(token.getType())) {
                result = token.getCard().getCountry();
            } else {
                throw new PaymentException("Unsupported token type acquired");
            }
        } catch (StripeException ex) {
            throw new PaymentException("Payment token not found");
        }
        return result;
    }

}
