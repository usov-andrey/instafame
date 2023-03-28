/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.cost.CostBean;
import com.xalap.crm.service.cost.CostService;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.ui.finance.period.MonthPeriodMap;
import com.xalap.crm.ui.finance.period.PeriodMap;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.column.GridColumnBuilder;
import com.xalap.vaadin.custom.grid.valueprovider.DoubleValueProvider;
import com.xalap.vaadin.custom.grid.valueprovider.IntegerValueProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import static com.xalap.crm.ui.finance.SalesBean.*;

/**
 * Финансовый отчет по месяцам
 *
 * @author Усов Андрей
 * @since 07/01/2020
 */
@Route(value = ProfitListFrame.VIEW_NAME, layout = MainLayout.class)
public class ProfitListFrame extends RootListFrame<ProfitGridBean> {
    public static final String VIEW_NAME = "profitList";

    @Autowired
    public ProfitListFrame(CostService costService, OrderService orderService) {
        super(bClass -> new GridPanel<>(ProfitGridBean.class));

        setMemoryDataProvider(() -> {

            PeriodMap<ProfitGridBean> resultMap = new MonthPeriodMap<>();
            //Какая прибыль от клиента за все время существования
            Map<ContactBean, Double> clientProfitMap = new HashMap<>();
            processOrders(orderService, resultMap, clientProfitMap);
            processCosts(costService, resultMap);

            for (ProfitGridBean value : resultMap.values()) {
                value.fillNewCustomers(clientProfitMap);
            }

            return resultMap.toList(ProfitGridBean::getPeriod);
        });
        GridColumnBuilder<ProfitGridBean> columns = gridPanel.columnBuilder();
        columns.add("Период", profitGridBean -> profitGridBean.getPeriod().startString());
        columns.addByNames(LEADS_F, SALES_F, REVENUE_F);
        columns.add("Средний чек", new DoubleValueProvider<>(ProfitGridBean::avgCheck));
        columns.add("Купивших клиентов", IntegerValueProvider.fromLong(SalesBean::getBoughtCustomers));
        columns.add("Конверсия в покупку", bean -> bean.conversionToCustomer() + "%");
        columns.add("Покупок на клиента", new DoubleValueProvider<>(ProfitGridBean::avgSalesPerCustomer));
        columns.add("Упущеная прибыль", new DoubleValueProvider<>(ProfitGridBean::lostProfit));
        columns.add(COST_PRICE);
        columns.add("Средняя наценка", profitGridBean -> profitGridBean.averageMarginPercent() + "%");
        columns.add("Средняя валовая прибыль", new DoubleValueProvider<>(ProfitGridBean::avgProfitPerSales));
        columns.add("Валовая прибыль", new DoubleValueProvider<>(ProfitGridBean::grossProfit));
        columns.add("Валовая маржа", profitGridBean -> profitGridBean.grossMarginPercent() + "%");
        columns.addByNames(COST_F, MARKETING_COST);
        columns.add("Новых клиентов", IntegerValueProvider.fromLong(ProfitGridBean::getNewBoughtCustomers));
        columns.add("LTV", new DoubleValueProvider<>(ProfitGridBean::ltv));
        columns.add("CAS", new DoubleValueProvider<>(ProfitGridBean::cas));
        columns.addComponent("LTV/CAS", profitGridBean -> {
            double value = profitGridBean.ltvCasRatio();
            var label = new Label(StringHelper.toString(value));
            UIUtils.setTextColor(value < 1.0 ? TextColor.ERROR : TextColor.SUCCESS, label);
            return label;
        });
        columns.add("Прибыль", new DoubleValueProvider<>(ProfitGridBean::profit));
        columns.add("Маржа", profitGridBean -> profitGridBean.marginPercent() + "%");
    }

    private void processCosts(CostService costService, PeriodMap<ProfitGridBean> resultMap) {
        for (CostBean costBean : costService.getAll()) {
            SalesBean bean = resultMap.computeIfAbsent(costBean.getCostTime(), ProfitGridBean::new);
            bean.setCost(bean.getCost() + costBean.getCost().doubleValue());
            if (costBean.getCostType() == null || costBean.getCostType().isMarketing()) {
                bean.setMarketingCost(bean.getMarketingCost() + costBean.getCost().doubleValue());
            }
        }
    }

    private void processOrders(OrderService orderService, PeriodMap<ProfitGridBean> resultMap, Map<ContactBean, Double> clientProfitMap) {
        for (OrderBean orderBean : orderService.getAll()) {
            SalesBean bean = resultMap.computeIfAbsent(orderBean.getCreateTime(), ProfitGridBean::new);
            ContactBean contact = orderBean.getLead().getContact();
            if (orderBean.getStatus().paid()) {
                if (orderBean.getRevenue() != null) {
                    bean.setRevenue(bean.getRevenue() + orderBean.getRevenue());
                }
                bean.setSales(bean.getSales() + 1);
                if (orderBean.getCostPrice() != null) {
                    bean.setCostPrice(bean.getCostPrice() + orderBean.getCostPrice());
                }

                bean.client(contact);
                clientProfitMap.put(contact, orderBean.profit() +
                        (clientProfitMap.containsKey(contact) ? clientProfitMap.get(contact) : 0));
            } else {
                bean.lead(contact);
            }
            bean.setLeads(bean.getLeads() + 1);
        }
    }

}
