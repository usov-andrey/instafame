/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.CollectionHelper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
@Service
public class ContactDataService extends CrudService<ContactDataBean, ContactDataRepository, Integer> {

    public ContactDataService() {
        super();
    }

    public List<ContactDataBean> sortByDataType(List<ContactDataBean> beanList) {
        Collections.sort(beanList, (o1, o2) -> {
            int i = o1.getDataType().compareTo(o2.getDataType());
            return i != 0 ? i : o1.getCreateTime().compareTo(o2.getCreateTime());
        });
        return beanList;
    }

    public List<ContactWithDataBean> getContactWithDataList(Map<ContactBean, List<ContactDataBean>> multiMap) {
        List<ContactWithDataBean> gridBeanList = new ArrayList<>();
        for (Map.Entry<ContactBean, List<ContactDataBean>> contactBeanListEntry : multiMap.entrySet()) {
            ContactWithDataBean gridBean = new ContactWithDataBean();
            gridBean.setContact(contactBeanListEntry.getKey());
            gridBean.setContactDataList(contactBeanListEntry.getValue());
            gridBeanList.add(gridBean);
        }
        return gridBeanList;
    }

    public Map<ContactBean, List<ContactDataBean>> getContactDataMap(List<ContactDataBean> dataBeanList) {
        return CollectionHelper.createMultiMap(dataBeanList, ContactDataBean::getContact, bean -> bean);
    }

    /**
     * Возвращает все контактные данные контакта, у которого есть в данных data
     */
    public List<ContactWithDataBean> getContactWithDataListByFilter(ContactData data) {
        Set<ContactBean> contacts = getContacts(data);
        return contacts.stream().map(this::getContactWithData).collect(Collectors.toList());
    }

    public Set<ContactBean> getContacts(ContactData data) {
        List<ContactDataBean> contactDataList = repository().findByDataTypeAndValue(data.getDataType(), data.getValue());
        return contactDataList.stream().map(ContactDataBean::getContact).collect(Collectors.toSet());
    }

    public ContactDataBean saveContactData(ContactBean contactBean, ContactData data) {
        return saveContactData(contactBean, data, new Date());
    }

    public ContactDataBean saveContactData(ContactBean contactBean, ContactData data, Date time) {
        List<ContactDataBean> contactDataBeanList = repository().findByContactAndDataType(contactBean, data.getDataType());
        for (ContactDataBean contactDataBean : contactDataBeanList) {
            if (contactDataBean.getValue().equalsIgnoreCase(data.getValue())) {
                return contactDataBean;
            }
        }
        ContactDataBean bean = new ContactDataBean();
        bean.setContact(contactBean);
        bean.setCreateTime(time);
        bean.setDataType(data.getDataType());
        bean.setValue(data.getValue());
        return save(bean);
    }

    public ContactWithDataBean save(ContactWithDataBean contactWithDataBean, ContactData data) {
        ContactDataBean contactDataBean = saveContactData(contactWithDataBean.getContact(), data);
        contactWithDataBean.add(contactDataBean);
        return contactWithDataBean;
    }

    /**
     * Возвращаем контакт с контактными данными
     */
    public ContactWithDataBean getContactWithData(ContactBean contactBean) {
        return new ContactWithDataBean(contactBean, repository().findByContact(contactBean));
    }
}
