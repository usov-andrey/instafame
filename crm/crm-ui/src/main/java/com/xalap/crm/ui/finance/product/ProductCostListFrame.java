/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance.product;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.order.product.OrderProductBean;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.service.product.ProductService;
import com.xalap.crm.ui.finance.SalesBean;
import com.xalap.crm.ui.finance.period.MonthPeriodMap;
import com.xalap.crm.ui.finance.period.PeriodMap;
import com.xalap.vaadin.custom.frame.RootListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.GridPanel;
import com.xalap.vaadin.custom.grid.valueprovider.DoubleValueProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Отчет по месяцам по продуктам
 *
 * @author Усов Андрей
 * @since 07/01/2020
 */
@Route(value = ProductCostListFrame.VIEW_NAME, layout = MainLayout.class)
public class ProductCostListFrame extends RootListFrame<ProductCostGridBean> {
    public static final String VIEW_NAME = "productCostList";

    @Autowired
    public ProductCostListFrame(ProductService productService, OrderService orderService,
                                OrderProductService orderProductService, OrderLineService orderLineService) {
        super((bClass) -> new GridPanel<>(ProductCostGridBean.class));

        setMemoryDataProvider(() -> {

            PeriodMap<ProductCostGridBean> resultMap = new MonthPeriodMap<>();
            Map<OrderBean, List<OrderProductBean>> productMap = orderProductService.getProductMap();
            List<OrderBean> orders = orderService.getAll();

            for (OrderBean orderBean : orders) {
                if (orderBean.getStatus().paid()) {
                    ProductCostGridBean bean = resultMap.computeIfAbsent(orderBean.getCreateTime(), ProductCostGridBean::new);
                    List<OrderProductBean> productBeanList = productMap.get(orderBean);
                    if (productBeanList != null) {
                        for (OrderProductBean orderProductBean : productBeanList) {
                            ProductBean product = orderProductBean.getProduct();
                            SalesBean salesBean = bean.getSalesBeanMap().computeIfAbsent(product, productBean -> new SalesBean());
                            salesBean.setSales(salesBean.getSales() + orderProductBean.getQuantity());
                            if (orderProductBean.getCost() != null) {
                                salesBean.setCostPrice(salesBean.getCostPrice() + orderProductBean.getCost());
                            }
                            salesBean.setRevenue(salesBean.getRevenue() + orderProductBean.getPrice());
                        }
                    }
                }
            }

            return resultMap.toList(ProductCostGridBean::getPeriod);
        });
        GridColumns<ProductCostGridBean> columns = gridPanel.columns();
        columns.add("Период", productCostGridBean -> productCostGridBean.getPeriod().startString());
        HeaderRow headerRow = gridPanel.grid().prependHeaderRow();
        for (ProductBean productBean : productService.getAll()) {
            Grid.Column<ProductCostGridBean> revenueColumn = columns.addColumn("Выручка", new DoubleValueProvider<>(productCostGridBean -> {
                SalesBean bean = productCostGridBean.getSalesBeanMap().get(productBean);
                return bean != null ? bean.getRevenue() : 0;
            }));
            Grid.Column<ProductCostGridBean> costPriceColumn = columns.addColumn("Себестоимость", new DoubleValueProvider<>(productCostGridBean -> {
                SalesBean bean = productCostGridBean.getSalesBeanMap().get(productBean);
                return bean != null ? bean.getCostPrice() : 0;
            }));
            Grid.Column<ProductCostGridBean> costPriceByUnitColumn = columns.addColumn("Себ. на единицу", new DoubleValueProvider<>(productCostGridBean -> {
                SalesBean bean = productCostGridBean.getSalesBeanMap().get(productBean);
                return bean != null ? bean.avgCostPricePerUnit() : 0;
            }));
            Grid.Column<ProductCostGridBean> profitColumn = columns.addColumn("Прибыль", new DoubleValueProvider<>(productCostGridBean -> {
                SalesBean bean = productCostGridBean.getSalesBeanMap().get(productBean);
                return bean != null ? bean.profit() : 0;
            }));
            Div header = new Div(new Span(productBean.getName()));
            //header.setSizeFull();
            headerRow.join(revenueColumn, costPriceColumn, costPriceByUnitColumn, profitColumn).setComponent(header);
        }

    }

}
