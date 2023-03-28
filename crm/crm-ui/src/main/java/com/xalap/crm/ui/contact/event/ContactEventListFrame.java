/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact.event;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.event.ContactEventBean;
import com.xalap.crm.service.contact.event.ContactEventService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Route(value = ContactEventListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("События")
public class ContactEventListFrame extends RootEntityListFrame<ContactEventBean> {
    public static final String VIEW_NAME = "contactEventList";

    @Autowired
    public ContactEventListFrame(ServiceRef<ContactEventService> service) {
        super(service, desc(ContactEventBean.EVENT_TIME));
        GridColumns<ContactEventBean> columns = gridPanel.columns();
        columns.add(ContactEventBean.EVENT_TIME, ContactEventBean.CLIENT_ID,
                ContactEventBean.EVENT_TYPE, ContactEventBean.TEXT_FIELD_NAME);
        addCreateButton();
    }

}
