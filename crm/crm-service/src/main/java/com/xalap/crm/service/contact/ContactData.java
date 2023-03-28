/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.domain.annotation.FieldName;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
@MappedSuperclass
public class ContactData implements Serializable {

    private static final long serialVersionUID = 1905122041950251657L;

    public static final String VALUE_FIELD_NAME = "value";
    public static final String DATA_TYPE = "dataType";
    @NotNull
    @FieldName(VALUE_FIELD_NAME)
    private String value;
    @NotNull
    @FieldName(DATA_TYPE)
    private ContactDataType dataType;

    public ContactData() {
    }

    public ContactData(@NotNull String value, @NotNull ContactDataType dataType) {
        this.value = value;
        this.dataType = dataType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContactDataType getDataType() {
        return dataType;
    }

    public void setDataType(ContactDataType dataType) {
        this.dataType = dataType;
    }

    public boolean same(ContactData data) {
        return value.equals(data.value) && dataType.equals(data.dataType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactData that = (ContactData) o;

        if (!value.equals(that.value)) return false;
        return dataType == that.dataType;

    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + dataType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ContactData{" +
                "value='" + value + '\'' +
                ", dataType=" + dataType +
                '}';
    }
}
