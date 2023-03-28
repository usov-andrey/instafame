/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.api;

import com.xalap.framework.domain.holder.IdHolder;

import java.math.BigDecimal;

/**
 * Счет Dto
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
public interface InvoiceDto extends InvoiceType, IdHolder<Long> {

    InvoiceId getInvoiceId();

    String getName();

    BigDecimal getAmount();

    String getCustomer();

    String getCustomerEmail();

}
