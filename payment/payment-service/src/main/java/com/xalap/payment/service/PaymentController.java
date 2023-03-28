/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.framework.data.dto.DtoCrudService;
import com.xalap.payment.api.InvoicePaymentHandler;
import com.xalap.payment.api.PaymentApi;
import com.xalap.payment.api.PaymentDto;
import com.xalap.payment.api.PaymentHtml;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;
import com.xalap.payment.service.provider.PaymentServiceProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Внешнее api платежей:
 * обработать информацию о платеже
 * список всех платежных систем
 * получить html для оплаты пополнения баланса
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Controller
@AllArgsConstructor
@RequestMapping(PaymentController.NAME)
public class PaymentController extends DtoCrudService<PaymentDto, Payment, PaymentService, Long> implements PaymentApi {

    public static final String NAME = "payments";

    private final PaymentService paymentService;
    private final InvoicePaymentHandlerStore invoicePaymentHandlerStore;

    @Override
    @GetMapping("providers")
    public
    @ResponseBody
    List<String> getPaymentServiceProviderNames() {
        return paymentService.getProviders().stream().map(PaymentServiceProvider::getName)
                .collect(Collectors.toList());
    }

    /**
     * обработать информацию о платеже
     */
    @PostMapping("providers/{providerName}")
    public
    @ResponseBody
    String createPayment(@PathVariable String providerName, HttpServletRequest request) {
        PaymentServiceProvider provider = getPaymentServiceProvider(providerName);
        Payment payment = provider.createPayment(request);
        paymentService.saveAndProcessPayment(payment);
        return provider.successPaymentResponse(request);
    }

    private PaymentServiceProvider getPaymentServiceProvider(String providerName) {
        return paymentService.getProvider(providerName);
    }

    @Override
    public PaymentHtml getPaymentHtml(String providerName, InvoiceDto invoice) {
        PaymentServiceProvider provider = getPaymentServiceProvider(providerName);
        return provider.getPaymentHtml(invoice);
    }

    @Override
    public void registerInvoicePaymentHandler(InvoicePaymentHandler invoicePaymentHandler) {
        invoicePaymentHandlerStore.addHandler(invoicePaymentHandler);
    }

    @Override
    public InvoiceId generateNewInvoiceId(String providerName) {
        PaymentServiceProvider provider = getPaymentServiceProvider(providerName);
        return provider.newInvoiceId();
    }
}
