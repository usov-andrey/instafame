/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.crm.order;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.order.orderline.OrderLineService;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.crm.ui.order.OrderFrame;
import com.xalap.instafame.service.InstaFameService;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
@Route(value = InstaFameOrderListFrame.VIEW_NAME, layout = MainLayout.class)
public class InstaFameOrderFrame extends OrderFrame {

    public InstaFameOrderFrame(ServiceRef<OrderService> service, ServiceRef<OrderProductService> orderProductService,
                               ServiceRef<OrderLineService> orderLineService,
                               ServiceRef<InstaFameService> instaFameService, ServiceRef<IOService> ioService) {
        super(service, orderProductService, orderLineService);
        //Заменяем вкладки Основная информация
        withTabs().addMainTab(new InstaFameOrderForm(service, instaFameService, ioService));
    }
}
