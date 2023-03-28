/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.vaadin.custom.dialog.ChoosingListDialog;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class ContactChoosingDialog extends ChoosingListDialog<ContactWithDataBean, ContactBean> {

    public ContactChoosingDialog(ServiceRef<ContactService> service, ServiceRef<ContactDataService> dataService) {
        super(ContactWithDataBean::getContact, aClass -> new ContactGridPanel(service, dataService));
    }
}
