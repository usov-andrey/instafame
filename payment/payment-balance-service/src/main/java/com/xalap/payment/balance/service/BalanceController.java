/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.balance.service;

import com.xalap.payment.balance.api.BalanceApi;
import com.xalap.payment.invoice.api.InvoiceType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.HtmlUtils;

import java.math.BigDecimal;

/**
 * Реализация api по работе с балансом
 *
 * @author Usov Andrey
 * @since 2020-05-27
 */
@Controller
@RequestMapping(BalanceController.NAME)
@AllArgsConstructor
public class BalanceController implements BalanceApi {

    public static final String NAME = "balances";

    private final BalanceService balanceService;
    private final BalanceInvoicePaymentHandler balanceInvoicePaymentHandler;


    @Override
    @GetMapping("{customer}")
    public BigDecimal getBalance(@PathVariable String customer) {
        return balanceService.getBalance(HtmlUtils.htmlEscape(customer));
    }

    @Override
    public InvoiceType getInvoiceType() {
        return new InvoiceType() {
            @Override
            public String getHandler() {
                return balanceInvoicePaymentHandler.getName();
            }

            @Override
            public String getDescription() {
                return "Пополнение баланса";
            }
        };
    }
}
