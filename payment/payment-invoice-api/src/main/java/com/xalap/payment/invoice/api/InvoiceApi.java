/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.api;

import com.xalap.framework.domain.page.PageableEntityService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Api работы со счетом
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
public interface InvoiceApi extends PageableEntityService<InvoiceDto, Long> {
    /**
     * Найти в базе invoice
     */
    Optional<InvoiceDto> getInvoice(InvoiceId invoiceId);

    /**
     * @param userLogin   от какого пользователя исходит платеж
     * @param amountValue сумму счета
     * @param invoiceType тип счета: например для пополнения баланса
     * @param invoiceId   ид счета
     * @return созданный в базе счет
     */
    InvoiceDto createAndSaveInvoice(String userLogin, BigDecimal amountValue, InvoiceType invoiceType, InvoiceId invoiceId);
}
