/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.balance.service;

import com.xalap.framework.data.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Формирует счет на пополнение баланса,
 * добавляет обработчик полученой оплаты для увеличения баланса
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
@Service
@AllArgsConstructor
public class BalanceService extends CrudService<Balance, BalanceRepository, Long> {

    public static final String BALANCE_CACHE = "balance";

    Balance getOrCreate(String customer) {
        Optional<Balance> byCustomer = repository().findByCustomer(customer);
        return byCustomer.orElseGet(() -> {
            Balance balance = new Balance();
            balance.setCustomer(customer);
            balance.setAmount(new BigDecimal(0));
            return balance;
        });
    }

    @Cacheable(BALANCE_CACHE)
    public BigDecimal getBalance(String customer) {
        return repository().findByCustomer(customer)
                .map(Balance::getAmount).orElse(new BigDecimal(0));
    }

    @Override
    protected void onChange(Balance bean) {
        evictAll(BALANCE_CACHE);
    }

}
