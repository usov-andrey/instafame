/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.framework.data.CrudService;
import com.xalap.payment.api.InvoicePaymentHandler;
import com.xalap.payment.api.PaymentStatus;
import com.xalap.payment.invoice.api.InvoiceApi;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.service.provider.PaymentServiceProvider;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author Usov Andrey
 * @since 2020-04-16
 */
@Service
@AllArgsConstructor
public class PaymentService extends CrudService<Payment, PaymentRepository, Long> {

    private final InvoiceApi invoiceService;
    private final ListableBeanFactory beanFactory;
    private final InvoicePaymentHandlerStore invoicePaymentHandlerStore;

    /**
     * @return Список PaymentServiceProvider отсортированных в алфавитном порядке по имени
     */
    List<PaymentServiceProvider> getProviders() {
        Map<String, PaymentServiceProvider> beansOfType = beanFactory.getBeansOfType(PaymentServiceProvider.class);
        ArrayList<PaymentServiceProvider> paymentServiceProviders = new ArrayList<>(beansOfType.values());
        paymentServiceProviders.sort(Comparator.comparing(PaymentServiceProvider::getName));
        return paymentServiceProviders;
    }

    /**
     * Платежный сервис по имени
     */
    PaymentServiceProvider getProvider(String provider) {
        for (PaymentServiceProvider paymentServiceProvider : getProviders()) {
            if (paymentServiceProvider.getName().equals(provider)) {
                return paymentServiceProvider;
            }
        }
        throw new IllegalArgumentException("Not found provider with name " + provider);
    }

    /**
     * Получен платеж, нужно его обработать
     */
    @Transactional
    public void saveAndProcessPayment(Payment payment) {
        Optional<InvoiceDto> byInvoiceId = invoiceService.getInvoice(payment.getInvoiceId());
        byInvoiceId.ifPresent(invoiceDto -> payment.setCustomer(invoiceDto.getCustomer()));
        save(payment);
        //Мы можем как-то обработать этот платеж
        byInvoiceId.ifPresent(invoiceDto -> processPayment(payment, invoiceDto));
    }

    private void processPayment(Payment payment, InvoiceDto invoice) {
        InvoicePaymentHandler handler = invoicePaymentHandlerStore.getHandler(invoice.getHandler());
        handler.handle(invoice, payment);
        payment.setStatus(PaymentStatus.processed);
        save(payment);
    }

}
