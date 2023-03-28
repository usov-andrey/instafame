/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.crm.ui.messaging.newchat.MessageListWindow;
import com.xalap.vaadin.custom.tab.Tab;
import com.xalap.vaadin.starter.ui.components.detailsdrawer.DetailsDrawer;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Список сообщений у контакта
 *
 * @author Usov Andrey
 * @since 30/01/2020
 */
public class ContactMessageListTab extends Tab<ContactBean> {

    private final ServiceRef<MessageThreadService> service;
    private final MessageListWindow messageListWindow;

    public ContactMessageListTab(ServiceRef<MessageThreadService> service, ServiceRef<MessageService> messageService,
                                 ServiceRef<EmailService> emailService,
                                 ServiceRef<MessageTemplateService> messageTemplateService,
                                 ServiceRef<InstagramDirectService> instagramDirectService) {
        this.service = service;
        messageListWindow = new MessageListWindow(messageService, emailService,
                messageTemplateService, instagramDirectService);
        DetailsDrawer detailsDrawerWithoutHeader = messageListWindow.createDetailsDrawerWithoutHeader();
        detailsDrawerWithoutHeader.setSizeFull();
        detailsDrawerWithoutHeader.setWidthFull();
        add(detailsDrawerWithoutHeader);
    }

    @Override
    public void setParentBean(ContactBean contactBean) {
        super.setParentBean(contactBean);
        messageListWindow.showDetails(service.get().messageThreadProvider(contactBean));
    }
}
