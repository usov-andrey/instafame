/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 07/01/2020
 */
public class SalesBean implements Serializable {

    static final String REVENUE_F = "revenue";
    static final String COST_PRICE = "costPrice";
    static final String SALES_F = "sales";
    static final String COST_F = "cost";
    static final String LEADS_F = "leads";
    static final String MARKETING_COST = "marketingCost";

    @Caption("Лидов")
    @FieldName(LEADS_F)
    private int leads;//Количество лидов
    @Caption("Продаж")
    @FieldName(SALES_F)
    private int sales;//Количество продаж/количество проданных единиц продукта
    @Caption("Выручка")
    @FieldName(REVENUE_F)
    private double revenue;
    @Caption("Себестоимость продаж")
    @FieldName(COST_PRICE)
    private double costPrice;//Себестоимость
    @Caption("Общие Затраты")
    @FieldName(COST_F)
    private double cost;
    @Caption("Затраты на маркетинг")
    @FieldName(MARKETING_COST)
    private double marketingCost;

    private final Map<ContactBean, Integer> clients = new HashMap<>();//Клиент-количество сделанных покупок

    /**
     * @return Прибыль
     */
    public double profit() {
        return grossProfit() - cost;
    }

    /**
     * @return Маржа
     */
    int marginPercent() {
        return percent(profit() / getRevenue());
    }

    /**
     * @return Средняя наценка
     */
    int averageMarginPercent() {
        return costPrice == 0d ? 0 : percent(revenue / costPrice);
    }

    /**
     * @return Валовая прибыль = Выручка - Себестоимость продаж
     */
    double grossProfit() {
        return revenue - costPrice;
    }

    /**
     * @return Валовая маржа = Валовая прибыль / Выручка * 100
     */
    int grossMarginPercent() {
        return percent(grossProfit() / revenue);
    }

    /**
     * @return Средняя себестоимость одной единицы
     */
    public double avgCostPricePerUnit() {
        return sales > 0 ? costPrice / sales : 0;
    }

    /**
     * @return Средний чек
     */
    double avgCheck() {
        return sales > 0 ? revenue / sales : 0;
    }

    /**
     * @return Средняя валовая прибыль
     */
    double avgProfitPerSales() {
        return sales > 0 ? grossProfit() / sales : 0;
    }

    private int percent(double value) {
        return (int) (value * 100);
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getLeads() {
        return leads;
    }

    public void setLeads(int leads) {
        this.leads = leads;
    }

    public void lead(ContactBean contactBean) {
        clients.putIfAbsent(contactBean, 0);
    }

    void client(ContactBean contactBean) {
        Integer value = clients.get(contactBean);
        if (value == null) {
            value = 0;
        }
        clients.put(contactBean, value + 1);
    }

    long getBoughtCustomers() {
        return clients.values().stream().filter(integer -> integer > 0).count();
    }

    Set<ContactBean> getNewBoughtCustomer(Function<Date, Boolean> inPeriodFunction) {
        return clients.entrySet().stream().filter(
                contactBeanIntegerEntry -> contactBeanIntegerEntry.getValue() > 0 &&
                        inPeriodFunction.apply(contactBeanIntegerEntry.getKey().getCreateTime())
        ).map(Map.Entry::getKey).collect(Collectors.toSet());
    }

    int conversionToCustomer() {
        return percent((double) getBoughtCustomers() / clients.size());
    }

    double avgSalesPerCustomer() {
        Integer sum = clients.values().stream().reduce(0, Integer::sum);
        return !clients.isEmpty() ? (double) sum / getBoughtCustomers() : 0;
    }

    /**
     * @return Упушенная прибыль из-за ушедших клиентов, оставивших заявку, но так и не оплативших
     */
    double lostProfit() {
        //Не купившие клиенты
        long lostClients = clients.size() - getBoughtCustomers();
        return lostClients * avgSalesPerCustomer() * avgProfitPerSales();
    }

    public double getMarketingCost() {
        return marketingCost;
    }

    public void setMarketingCost(double marketingCost) {
        this.marketingCost = marketingCost;
    }

}
