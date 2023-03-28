/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactData;
import com.xalap.crm.service.contact.ContactDataBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import static com.xalap.crm.service.contact.ContactData.VALUE_FIELD_NAME;

/**
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class ContactDataListTab extends ListDetailsTab<ContactDataBean, ContactBean> {

    public ContactDataListTab(ContactFrame parentFrame, ServiceRef<ContactDataService> service) {
        super(parentFrame, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> service.get().getContactWithData(getParentBean()).getContactDataList());
        gridPanel.columns().add(ContactDataBean.CREATE_TIME, ContactData.DATA_TYPE, VALUE_FIELD_NAME,
                ContactDataBean.EXPIRE_DATE);
        addCreateButton(() -> {
            ContactDataBean bean = new ContactDataBean();
            bean.setContact(getParentBean());
            return bean;
        });
    }
}
