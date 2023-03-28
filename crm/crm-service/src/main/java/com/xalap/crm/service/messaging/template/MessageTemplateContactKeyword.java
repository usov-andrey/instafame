/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.template;

import com.xalap.crm.service.contact.ContactDataHolder;
import com.xalap.framework.utils.RandomUtils;

/**
 * @author Усов Андрей
 * @since 07/06/2019
 */
public enum MessageTemplateContactKeyword {

    name {
        @Override
        protected String getValue(ContactDataHolder contactDataHolder) {
            return contactDataHolder.getName();
        }
    }, email {
        @Override
        protected String getValue(ContactDataHolder contactDataHolder) {
            return contactDataHolder.getEmail();
        }
    }, instagram {
        @Override
        protected String getValue(ContactDataHolder contactDataHolder) {
            return contactDataHolder.getInstagram();
        }
    }, hello {
        @Override
        protected String getValue(ContactDataHolder contactDataHolder) {
            String name = contactDataHolder.getName();
            String instagram = contactDataHolder.getInstagram();
            return (name.equalsIgnoreCase(instagram) ?
                    "Здравствуйте" :
                    (RandomUtils.nextBoolean() ? "Здравствуйте, " + name : name + ", здравствуйте")) +
                    ".";
        }
    };

    abstract protected String getValue(ContactDataHolder contactDataHolder);
}
