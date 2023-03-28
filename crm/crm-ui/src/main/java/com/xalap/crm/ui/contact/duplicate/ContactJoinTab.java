/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact.duplicate;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.ui.contact.ContactChoosingDialog;
import com.xalap.crm.ui.contact.ContactFrame;
import com.xalap.vaadin.custom.component.Form;
import com.xalap.vaadin.custom.component.LookupField;
import com.xalap.vaadin.custom.tab.Tab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Обьединение контактов
 *
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class ContactJoinTab extends Tab<ContactBean> {

    private ContactBean deletingContact;

    public ContactJoinTab(ServiceRef<ContactService> service, ServiceRef<ContactDataService> dataService) {
        Form form = new Form();
        add(form);

        LookupField<ContactBean, Integer> lookupField = new LookupField<>("Контакт, в который переносить данные", ContactFrame.class,
                new ContactChoosingDialog(service, dataService));
        form.add(lookupField);

        form.setButtons(
                new Button("Удалить текущий и перенести все в выбранный", event -> {
                    if (lookupField.getValue() != null) {
                        ContactBean targetContact = lookupField.getValue();
                        service.get().joinContacts(deletingContact, targetContact);
                        UI.getCurrent().navigate(ContactFrame.class, targetContact.getId());
                    }
                })
        );
    }


    @Override
    public void setParentBean(ContactBean bean) {
        deletingContact = bean;
    }
}
