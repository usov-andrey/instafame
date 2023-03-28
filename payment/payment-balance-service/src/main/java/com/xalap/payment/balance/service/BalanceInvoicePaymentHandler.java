/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.balance.service;

import com.xalap.payment.api.InvoicePaymentHandler;
import com.xalap.payment.api.PaymentApi;
import com.xalap.payment.api.PaymentDto;
import com.xalap.payment.invoice.api.InvoiceDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Обработчик, который при поступлении платежа увеличивает сумму на балансе
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
@Service
public class BalanceInvoicePaymentHandler implements InvoicePaymentHandler {

    private final BalanceService balanceService;

    public BalanceInvoicePaymentHandler(BalanceService balanceService, PaymentApi paymentApi) {
        this.balanceService = balanceService;
        paymentApi.registerInvoicePaymentHandler(this);
    }

    @Override
    public void handle(InvoiceDto invoice, PaymentDto payment) {
        Balance balance = balanceService.getOrCreate(invoice.getCustomer());
        BigDecimal addAmount = payment.getCustomerPayAmount();//На баланс добавляем без коммиссси
        balance.setAmount(balance.getAmount().add(addAmount));
        balanceService.save(balance);
    }
}
