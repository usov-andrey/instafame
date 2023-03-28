/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.ui.finance.period.StartPeriod;
import com.xalap.framework.utils.DateUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Усов Андрей
 * @since 07/01/2020
 */
class ProfitGridBean extends SalesBean {

    private final StartPeriod period;
    private final Map<ContactBean, Double> newCustomerProfitMap = new HashMap<>(); //Для каждого нового клиента хранится сколько он принес валовой прибыли

    ProfitGridBean(StartPeriod period) {
        this.period = period;
    }

    StartPeriod getPeriod() {
        return period;
    }

    void fillNewCustomers(Map<ContactBean, Double> allCustomerProfitMap) {
        Set<ContactBean> newBoughtCustomers = getNewBoughtCustomer(date -> period.inPeriod(DateUtils.localDate(date)));
        for (ContactBean newBoughtCustomer : newBoughtCustomers) {
            newCustomerProfitMap.put(newBoughtCustomer, allCustomerProfitMap.get(newBoughtCustomer));
        }
    }

    /**
     * @return Количество клиентов первый раз купивших в этот период
     */
    long getNewBoughtCustomers() {
        return newCustomerProfitMap.size();
    }

    /**
     * Стоимость привлечения одного клиента
     */
    double cas() {
        return divine(getMarketingCost(), getNewBoughtCustomers());
    }

    /**
     * @return Доход от одного клиента - затраты на привлечение и удержание
     */
    double ltv() {
        return divine(profitNewCustomers(), getNewBoughtCustomers());
    }

    /**
     * @return ltv / cas
     */
    double ltvCasRatio() {
        return divine(ltv(), cas());
    }

    private double divine(double v1, double v2) {
        if (v2 == 0) {
            return 0;
        }
        return v1 / v2;
    }

    private double profitNewCustomers() {
        return newCustomerProfitMap.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
