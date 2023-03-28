/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.utils.StringHelper;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
public enum ContactDataType {

    email,
    googleClientId,//идентификатор устройства
    instagram,
    phone,
    site,
    yandexId,
    facebook,
    telegram,
    twitter,
    linkedIn;

    public ContactData data(String value) {
        if (StringHelper.isEmpty(value)) {
            throw new IllegalStateException(name() + " is empty: " + value);
        }
        return new ContactData(value, this);
    }
}
