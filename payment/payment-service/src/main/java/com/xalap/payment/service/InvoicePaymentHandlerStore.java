/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.service;

import com.xalap.payment.api.InvoicePaymentHandler;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Хранилище для InvoicePyamentHandler's
 *
 * @author Usov Andrey
 * @since 17.05.2021
 */
@Service
public class InvoicePaymentHandlerStore {

    private final Map<String, InvoicePaymentHandler> map = new HashMap<>();

    InvoicePaymentHandler getHandler(String handler) {
        return map.get(handler);
    }

    void addHandler(InvoicePaymentHandler invoicePaymentHandler) {
        map.put(invoicePaymentHandler.getName(), invoicePaymentHandler);
    }
}
