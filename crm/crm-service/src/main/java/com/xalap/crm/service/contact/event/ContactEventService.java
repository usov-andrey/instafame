/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact.event;

import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Service
public class ContactEventService extends CrudService<ContactEventBean, ContactEventRepository, Integer> {

    public ContactEventService() {
        super();
    }
}
