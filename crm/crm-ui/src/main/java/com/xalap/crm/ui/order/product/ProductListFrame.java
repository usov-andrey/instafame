/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order.product;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.product.ProductBean;
import com.xalap.crm.service.product.ProductService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 22/10/2019
 */
@Route(value = ProductListFrame.VIEW_NAME, layout = MainLayout.class)
public class ProductListFrame extends RootEntityListFrame<ProductBean> {

    public static final String VIEW_NAME = "products";

    @Autowired
    public ProductListFrame(ServiceRef<ProductService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));
        GridColumns<ProductBean> columns = gridPanel.columns();

        columns.addLink("Название", ProductBean::getName, ProductBean::getId, ProductFrame.class);
        columns.add(ProductBean.CODE);

        addCreateButton();
    }
}
