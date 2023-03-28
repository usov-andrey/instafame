/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.ui.order.OrderGridPanel;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 21/06/2019
 */
public class ContactOrderListTab extends ListDetailsTab<OrderBean, ContactBean> {
    public ContactOrderListTab(ContactFrame contactFrame, ServiceRef<OrderService> orderService) {
        super(aClass -> new OrderGridPanel(orderService), contactFrame, orderService);
        gridPanel.dataSource().setMemoryDataProvider(() -> orderService.get().getOrders(getParentBean()));
    }
}
