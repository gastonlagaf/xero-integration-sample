package com.gastonlagaf.xero.controller.error;

import com.gastonlagaf.xero.exception.PaymentException;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ErrorHandler {

    @SneakyThrows
    @ExceptionHandler(PaymentException.class)
    public void handlePaymentException(HttpServletResponse response, PaymentException exception) {
        response.sendError(HttpServletResponse.SC_PAYMENT_REQUIRED, exception.getMessage());
    }

}
