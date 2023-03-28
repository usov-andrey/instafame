/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.api;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.payment.invoice.api.InvoiceId;

import java.math.BigDecimal;

/**
 * Dto платежа
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
public interface PaymentDto extends IdHolder<Long> {
    String getName();

    InvoiceId getInvoiceId();

    String getCurrencyLabel();

    String getPaymentMethod();

    BigDecimal getFee();

    BigDecimal getCustomerPayAmount();

    String getCustomerEmail();

    PaymentStatus getStatus();
}
