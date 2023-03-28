/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.order;

import com.vaadin.flow.component.html.Label;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.ui.lead.LeadFrame;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.dialog.EntityChoosingListDialog;
import com.xalap.vaadin.custom.grid.GridColumns;

/**
 * @author Усов Андрей
 * @since 18/06/2019
 */
public class OrderChoosingListDialog extends EntityChoosingListDialog<OrderBean> {

    public OrderChoosingListDialog(OrderService orderService) {
        gridPanel.dataSource().setMemoryDataProvider(orderService::getAll);

        gridPanel.filters()
                .addDate("C", (orderBean, date) -> orderBean.getCreateTime().after(date))
                .addDate("По", (orderBean, date) -> orderBean.getCreateTime().before(date));

        GridColumns<OrderBean> columns = gridPanel.columns();
        columns.addColumn(OrderBean.CREATE_TIME).setWidth("130px");
        columns.addColumn(OrderBean.ORDER_ID).setWidth("100px");

        columns.addLink("Заявка", bean -> bean.getLead() != null ? bean.getLead().getName() : "",
                bean -> bean.getLead() != null ? bean.getLead().getId() : 0, LeadFrame.class);

        columns.addComponent("Инстаграм", orderBean -> orderBean.getLead() != null
                && orderBean.getLead().getInstagram() != null ?
                new InstagramComponent(orderBean.getLead().getInstagram()) : new Label());

        columns.addColumn(OrderBean.REVENUE);

        columns.addColumn(OrderBean.STATUS).setWidth("70px");
    }
}
