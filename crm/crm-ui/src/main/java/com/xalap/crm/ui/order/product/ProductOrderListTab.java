/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order.product;

import com.xalap.crm.service.order.product.OrderProductBean;
import com.xalap.crm.service.order.product.OrderProductService;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 22/10/2019
 */
public class ProductOrderListTab extends ListDetailsTab<OrderProductBean, ProductBean> {

    public ProductOrderListTab(ProductFrame productFrame, ServiceRef<OrderProductService> service) {
        super(productFrame, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> service.get().repository().findByProduct(getParentBean()));
        gridPanel.createColumns();
    }
}
