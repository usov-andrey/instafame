/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.messaging.thread;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageChannel;
import com.xalap.crm.service.messaging.MessageService;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Хранит информацию о теме сообщений
 *
 * @author Усов Андрей
 * @since 06/06/2019
 */
public class MessageThreadProvider implements Serializable {
    private static final long serialVersionUID = 1905122041950251657L;

    private final ContactWithDataBean contactWithDataBean;
    private MessageThreadBean bean;

    public MessageThreadProvider(ContactDataService contactDataService, MessageThreadBean bean) {
        this(bean.getContact(), contactDataService);
        this.bean = bean;
    }

    public MessageThreadProvider(MessageThreadService service, ContactDataService contactDataService, ContactBean contactBean) {
        this(contactBean, contactDataService);
        Optional<MessageThreadBean> thread = service.getThread(contactBean);
        thread.ifPresent(messageThreadBean -> bean = messageThreadBean);
    }

    private MessageThreadProvider(ContactBean contactBean, ContactDataService contactDataService) {
        this.contactWithDataBean = contactDataService.getContactWithData(contactBean);
    }

    public MessageThreadBean getBean() {
        return bean;
    }

    public MessageThreadBean getOrCreateBean(MessageThreadService service) {
        if (bean == null) {
            bean = service.createThread(contactWithDataBean.getContact());
        }
        return bean;
    }

    /**
     * @return Уже сохраненные в базе id сообщений
     */
    public Set<String> getMessageItemIds(MessageService messageService, MessageChannel channel) {
        if (bean != null) {
            List<MessageBean> messages = messageService.getMessages(channel, bean);
            return messages.stream()
                    .filter(messageBean -> messageBean.getInternalId() != null)//У не отправленных сообщений будет internalId = null
                    .map(MessageBean::getInternalId).collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    public ContactWithDataBean getContactWithData() {
        return contactWithDataBean;
    }

    public ContactBean getContact() {
        return contactWithDataBean.getContact();
    }

}
