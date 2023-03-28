/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
public class ContactWithDataBean implements IdHolder<Integer>, ContactDataHolder, Serializable {

    public static final String CONTACT_FIELD_NAME = "contact";
    @FieldName(CONTACT_FIELD_NAME)
    private ContactBean contact;
    private final ArrayList<ContactDataBean> contactDataList = new ArrayList<>();

    public ContactWithDataBean() {
    }

    public ContactWithDataBean(ContactBean contact, ContactDataBean contactData) {
        this(contact, Collections.singletonList(contactData));
    }

    public ContactWithDataBean(ContactBean contact, List<ContactDataBean> contactDataList) {
        this.contact = contact;
        this.contactDataList.addAll(contactDataList);
    }

    @Override
    public Integer getId() {
        return contact.getId();
    }

    @Override
    public void setId(Integer id) {
        contact.setId(id);
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public List<ContactDataBean> getContactDataList() {
        return contactDataList;
    }

    public void setContactDataList(List<ContactDataBean> contactDataList) {
        this.contactDataList.addAll(contactDataList);
    }

    public boolean contains(ContactData data) {
        for (ContactDataBean contactDataBean : contactDataList) {
            if (contactDataBean.same(data)) {
                return true;
            }
        }
        return false;
    }

    private String getValue(ContactDataType dataType) {
        Optional<ContactDataBean> data = getData(dataType);
        return data.isPresent() ? data.get().getValue() : "";
    }

    public String getInstagram() {
        return getValue(ContactDataType.instagram);
    }

    public String getEmail() {
        return getValue(ContactDataType.email);
    }

    public String getClientId() {
        return getValue(ContactDataType.googleClientId);
    }

    private Optional<ContactDataBean> getData(ContactDataType dataType) {
        for (ContactDataBean contactDataBean : contactDataList) {
            if (contactDataBean.getDataType().equals(dataType)) {
                return Optional.of(contactDataBean);
            }
        }
        return Optional.empty();
    }

    public void add(ContactDataBean contactDataBean) {
        contactDataList.add(contactDataBean);
    }

    public boolean contains(String s) {
        for (ContactDataBean contactDataBean : contactDataList) {
            if (contactDataBean.getValue().contains(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getName() {
        return getContact().getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactWithDataBean that = (ContactWithDataBean) o;

        return contact != null ? contact.equals(that.contact) : that.contact == null;

    }

    @Override
    public int hashCode() {
        return contact != null ? contact.hashCode() : 0;
    }
}
