package com.gastonlagaf.xero.controller;

import com.gastonlagaf.xero.controller.model.InvoiceDto;
import com.gastonlagaf.xero.controller.model.request.CreateAccountingContactRequest;
import com.gastonlagaf.xero.controller.model.request.CreateInvoiceRequest;
import com.gastonlagaf.xero.controller.model.request.InvoicePaymentRequest;
import com.gastonlagaf.xero.entity.Invoice;
import com.gastonlagaf.xero.service.AccountingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/accounting")
public class AccountingController {

    private final AccountingService accountingService;

    @PostMapping("/invoice")
    public InvoiceDto createInvoice(@RequestBody CreateInvoiceRequest request) {
        Invoice invoice = accountingService.createInvoice(request.getEmail(), request.getType(), request.getCountryCode());
        return new InvoiceDto(invoice);
    }

    @PostMapping("/invoice/{invoiceId}/pay")
    public InvoiceDto payInvoice(@PathVariable Long invoiceId, @RequestBody InvoicePaymentRequest request) {
        Invoice invoice = accountingService.payInvoice(invoiceId, request.getPaymentToken());
        return new InvoiceDto(invoice);
    }

    @PostMapping("/contact")
    public void createContact(@RequestBody CreateAccountingContactRequest request) {
        accountingService.createContact(request.getEmail());
    }

}
