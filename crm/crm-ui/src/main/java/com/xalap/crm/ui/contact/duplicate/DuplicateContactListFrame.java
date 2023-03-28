/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact.duplicate;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.*;
import com.xalap.crm.ui.contact.ContactGridPanel;
import com.xalap.vaadin.custom.frame.RootBeanListFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
@Route(value = DuplicateContactListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Дубликаты контактов")
public class DuplicateContactListFrame extends RootBeanListFrame<ContactBean, ContactWithDataBean> {
    public static final String VIEW_NAME = "duplicateContactList";

    @Autowired
    public DuplicateContactListFrame(ServiceRef<ContactService> service, ServiceRef<ContactDataService> dataService) {
        super((aClass) -> new ContactGridPanel(service, dataService), ContactWithDataBean::getContact, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> {
            ContactDataService contactDataService = dataService.get();
            List<ContactDataBean> all = contactDataService.getAll();
            Map<ContactData, ContactBean> map = new HashMap<>();
            Set<ContactBean> contactsWithDuplicates = new HashSet<>();
            for (ContactDataBean dataBean : all) {
                ContactData data = new ContactData(dataBean.getValue(), dataBean.getDataType());
                ContactBean contact = dataBean.getContact();
                ContactBean contactInMap = map.get(data);
                if (contactInMap != null && !contactInMap.equals(contact)) {
                    //Значит с такими контактными данными несколько контактов
                    contactsWithDuplicates.add(contact);
                    contactsWithDuplicates.add(contactInMap);
                } else {
                    map.put(data, contact);
                }
            }
            all = all.stream().filter(contactDataBean -> contactsWithDuplicates.contains(contactDataBean.getContact()))
                    .collect(Collectors.toList());
            Map<ContactBean, List<ContactDataBean>> multiMap = contactDataService.getContactDataMap(all);
            return contactDataService.getContactWithDataList(multiMap);
        });
    }

}
