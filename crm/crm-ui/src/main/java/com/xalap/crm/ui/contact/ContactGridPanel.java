/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.crm.service.contact.*;
import com.xalap.framework.utils.DateHelper;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Грид списка проектов
 *
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class ContactGridPanel extends BeanWithIdGridPanel<ContactWithDataBean, Integer> {

    public ContactGridPanel(ServiceRef<ContactService> service, ServiceRef<ContactDataService> dataService) {
        super(ContactWithDataBean.class);
        sortWithId();

        filters().addText("Поиск", (contactWithDataBean, s) -> contactWithDataBean.getContact().getName().contains(s) ||
                contactWithDataBean.contains(s));

        dataSource().setMemoryDataProvider(() -> {
            ContactDataService contactDataService = dataService.get();
            Map<ContactBean, List<ContactDataBean>> multiMap = contactDataService.getContactDataMap(contactDataService.getAll());
            service.get().getAll().stream().filter(contactBean -> !multiMap.containsKey(contactBean))
                    .forEach(contactBean -> multiMap.put(contactBean, Collections.emptyList()));
            return contactDataService.getContactWithDataList(multiMap);
        });

        GridColumns<ContactWithDataBean> columns = columns();
        columns.addLink("Создан", (ValueProvider<ContactWithDataBean, String>) contactWithDataBean ->
                DateHelper.getDateTime(contactWithDataBean.getContact().getCreateTime()),
                    contactWithDataBean -> contactWithDataBean.getContact().getId(), ContactFrame.class);
        columns.add("Имя", bean -> bean.getContact().getName());
        columns.addComponent("Контакты",  contactGridBean -> {
            VerticalLayout layout = layout();
            List<ContactDataBean> dataBeanList = dataService.get().sortByDataType(contactGridBean.getContactDataList());
            for (ContactDataBean contactDataBean : dataBeanList) {
                if (contactDataBean.getDataType() != ContactDataType.googleClientId) {
                    layout.add(getContactDataComponent(contactDataBean));
                }
            }
            return layout;
        });
        columns.addComponent("ClientId", contactGridBean -> {
            VerticalLayout layout = layout();
            List<ContactDataBean> dataBeanList = dataService.get().sortByDataType(contactGridBean.getContactDataList());
            for (ContactDataBean contactDataBean : dataBeanList) {
                if (contactDataBean.getDataType() == ContactDataType.googleClientId) {
                    layout.add(new Label(contactDataBean.getValue()));
                }
            }
            return layout;
        });
    }

    private VerticalLayout layout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(false);
        layout.setPadding(false);
        return layout;
    }

    private Component getContactDataComponent(ContactDataBean bean) {
        if (bean.getExpireDate() != null) {
            Label label = new Label(bean.getValue());
            UIUtils.setTextColor(TextColor.ERROR, label);
            return label;
        }
        if (bean.getDataType() == ContactDataType.instagram) {
            return new InstagramComponent(bean.getValue());
        }
        return new Label(bean.getValue());
    }
}
