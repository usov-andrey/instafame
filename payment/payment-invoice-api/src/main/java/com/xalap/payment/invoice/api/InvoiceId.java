/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.api;


import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Составной идентификатор счета: платежный сервис + id счета в этом платежном сервисе
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Embeddable
public class InvoiceId implements Serializable {

    @NotNull
    private String paymentServiceProvider;
    @NotNull
    private String pspInvoiceId;//id счета уникальный в рамках каждого paymentServiceProvider

    public String getPaymentServiceProvider() {
        return paymentServiceProvider;
    }

    public void setPaymentServiceProvider(String paymentServiceProvider) {
        this.paymentServiceProvider = paymentServiceProvider;
    }

    public String getPspInvoiceId() {
        return pspInvoiceId;
    }

    public void setPspInvoiceId(String pspInvoiceId) {
        this.pspInvoiceId = pspInvoiceId;
    }

    public String name() {
        return pspInvoiceId + "(" + paymentServiceProvider + ")";
    }

    @Override
    public String toString() {
        return "InvoiceId{" +
                "paymentServiceProvider='" + paymentServiceProvider + '\'' +
                ", pspInvoiceId='" + pspInvoiceId + '\'' +
                '}';
    }
}
