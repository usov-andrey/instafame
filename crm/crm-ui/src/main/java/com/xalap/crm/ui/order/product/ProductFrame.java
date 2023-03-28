/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order.product;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.service.product.ProductService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 22/10/2019
 */
@Route(value = ProductListFrame.VIEW_NAME, layout = MainLayout.class)
public class ProductFrame extends RootEntityFrame<ProductBean, Integer> {

    @Autowired
    public ProductFrame(ServiceRef<ProductService> service, ServiceRef<OrderProductService> orderProductService) {
        super(service, ProductListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
                .addTab("Заказы", new ProductOrderListTab(this, orderProductService)
                ));
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
