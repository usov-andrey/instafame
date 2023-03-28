/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.thread;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.framework.data.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Сервис треда сообщений
 *
 * @author Усов Андрей
 * @since 04/06/2019
 */
@Service
public class MessageThreadService extends CrudService<MessageThreadBean, MessageThreadRepository, Integer> {

    @Autowired
    private ContactDataService contactDataService;
    @Autowired
    private ContactService contactService;

    public MessageThreadService() {
        super();
    }

    public MessageThreadProvider messageThreadProvider(Integer contactId) {
        var contactBean = contactService.get(contactId);
        return messageThreadProvider(contactBean);
    }

    public MessageThreadProvider messageThreadProvider(ContactBean contactBean) {
        return new MessageThreadProvider(this, contactDataService, contactBean);
    }

    public MessageThreadProvider messageThreadProvider(MessageThreadBean currentThread) {
        return new MessageThreadProvider(contactDataService, currentThread);
    }

    public Optional<MessageThreadBean> getThread(ContactBean contactBean) {
        MessageThreadBean byContact = getRepository().findByContact(contactBean);
        return byContact != null ? Optional.of(byContact) : Optional.empty();
    }

    public MessageThreadBean createThread(ContactBean contactBean) {
        MessageThreadBean bean = new MessageThreadBean();
        bean.setContact(contactBean);
        return save(bean);
    }

}
