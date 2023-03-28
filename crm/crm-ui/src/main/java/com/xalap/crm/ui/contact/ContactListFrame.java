/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.crm.ui.contact.duplicate.DuplicateContactListFrame;
import com.xalap.vaadin.custom.frame.RootBeanListFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 08/06/2019
 */
@Route(value = ContactListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Контакты")
public class ContactListFrame extends RootBeanListFrame<ContactBean, ContactWithDataBean> {
    public static final String VIEW_NAME = "contactList";

    @Autowired
    public ContactListFrame(ServiceRef<ContactService> service, ServiceRef<ContactDataService> dataService) {
        super((aClass) -> new ContactGridPanel(service, dataService), ContactWithDataBean::getContact, service);
        addCreateButton();
        gridPanel.buttons().add("Найти дубликатов", () -> UI.getCurrent().navigate(DuplicateContactListFrame.class));
        gridPanel.columns().actions((actions, bean) -> {
            actions.add("Написать", () -> navigate(ContactMessageListFrame.class, bean.getContact().getId()));
        });
    }

}
