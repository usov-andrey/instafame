/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.balance.api;

import com.xalap.payment.invoice.api.InvoiceType;

import java.math.BigDecimal;

/**
 * Api по работе с денежным балансом пользователя
 *
 * @author Usov Andrey
 * @since 17.05.2021
 */
public interface BalanceApi {

    /**
     * Баланс пользователя
     */
    BigDecimal getBalance(String customer);

    /**
     * Тип счета для пополнения баланса
     */
    InvoiceType getInvoiceType();
}
