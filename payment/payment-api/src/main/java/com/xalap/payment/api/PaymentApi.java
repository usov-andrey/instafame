/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.api;

import com.xalap.framework.domain.page.PageableEntityService;
import com.xalap.payment.invoice.api.InvoiceDto;
import com.xalap.payment.invoice.api.InvoiceId;

import java.util.List;

/**
 * Api модуля платежей
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
public interface PaymentApi extends PageableEntityService<PaymentDto, Long> {

    /**
     * Регистрация обработчика при поступлении платежа по счету
     */
    void registerInvoicePaymentHandler(InvoicePaymentHandler invoicePaymentHandler);

    /**
     * Генериируем уникальный номер счета
     */
    InvoiceId generateNewInvoiceId(String paymentServiceProviderName);

    /**
     * @return список всех платежных систем
     */
    List<String> getPaymentServiceProviderNames();

    /**
     * Html который нужно отобразить пользователю для оплаты счета
     */
    PaymentHtml getPaymentHtml(String providerName, InvoiceDto invoice);
}
