/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.product.OrderProductBean;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.vaadin.custom.grid.valueprovider.DoubleValueProvider;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 29/04/2019
 */
public class OrderProductListTab extends ListDetailsTab<OrderProductBean, OrderBean> {

    public OrderProductListTab(OrderFrame orderFrame, ServiceRef<OrderProductService> service) {
        super(orderFrame, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> service.get().getProducts(getParentBean()));
        gridPanel.columns().add("Название", orderProductBean -> orderProductBean.getProduct().getName())
                .add("Цена", new DoubleValueProvider<>(OrderProductBean::getPrice))
                .add("Себестоимость", new DoubleValueProvider<>(OrderProductBean::getCost))
                .add(OrderProductBean.QUANTITY);
    }
}
