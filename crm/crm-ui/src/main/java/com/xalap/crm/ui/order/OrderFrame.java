/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
@Route(value = OrderListFrame.VIEW_NAME, layout = MainLayout.class)
public class OrderFrame extends RootEntityFrame<OrderBean, Integer> {

    @Autowired
    public OrderFrame(ServiceRef<OrderService> service, ServiceRef<OrderProductService> orderProductService,
                      ServiceRef<OrderLineService> orderLineService) {
        super(service, OrderListFrame.class);

        setViewContent(
                withTabs()
                        .addMainTab(service)
                .addTab("Позиции заказа", new OrderLineListTab(this, orderLineService))
                        .addTab("Продукты заказа", new OrderProductListTab(this, orderProductService)));
    }


    @Override
    protected String getTitle() {
        return "Заказ " + getBean().getOrderId() + " от " + getBean().getLead().getName();
    }
}
