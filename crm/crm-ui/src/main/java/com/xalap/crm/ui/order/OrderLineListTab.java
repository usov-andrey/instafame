/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.vaadin.custom.grid.valueprovider.DoubleValueProvider;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 31/10/2019
 */
public class OrderLineListTab extends ListDetailsTab<OrderLineBean, OrderBean> {

    public OrderLineListTab(OrderFrame orderFrame, ServiceRef<OrderLineService> service) {
        super(orderFrame, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> service.get().getOrderLines(getParentBean()));
        gridPanel.columns().add("Название", OrderLineBean::getLineText)
                .add("Цена", new DoubleValueProvider<>(OrderLineBean::getPrice))
                .add("Себестоимость", new DoubleValueProvider<>(OrderLineBean::getCost))
                .add(OrderLineBean.QUANTITY);
        addCreateButton(() -> {
            OrderLineBean bean = new OrderLineBean();
            bean.setOrder(getParentBean());
            return bean;
        });
    }
}

