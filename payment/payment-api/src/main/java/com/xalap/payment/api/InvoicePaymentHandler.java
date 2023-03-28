/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.api;

import com.xalap.payment.invoice.api.InvoiceDto;

/**
 * Обработчик счета при поступлении платежа по этому счету
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
public interface InvoicePaymentHandler {

    /**
     * Обработать полученный платеж по счету
     */
    void handle(InvoiceDto invoice, PaymentDto payment);


    default String getName() {
        return getClass().getName();
    }
}
