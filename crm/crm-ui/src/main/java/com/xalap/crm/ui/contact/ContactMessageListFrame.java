/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.crm.ui.messaging.chat.ChatComponent;
import com.xalap.crm.ui.messaging.newchat.MessageThreadListFrame;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 06/06/2019
 */
@Route(value = ContactMessageListFrame.VIEW_NAME, layout = MainLayout.class)
public class ContactMessageListFrame extends RootEntityFrame<ContactBean, Integer> {

    public static final String VIEW_NAME = "contactMessages";

    private final ChatComponent chatComponent;

    @Autowired
    public ContactMessageListFrame(ServiceRef<ContactService> contactService, ServiceRef<MessageThreadService> messageThreadService,
                                   ServiceRef<MessageService> messageService, ServiceRef<InstagramDirectService> instagramDirectService,
                                   ServiceRef<MessageTemplateService> messageTemplateService, ServiceRef<EmailService> emailService) {
        super(contactService, MessageThreadListFrame.class);
        chatComponent = new ChatComponent(messageThreadService, messageService, instagramDirectService, messageTemplateService, emailService);
        setViewContent(box(chatComponent.getMessageWindow()));
        setViewFooter(box(chatComponent.getInputBar()));
        addListener(value -> chatComponent.setContact(value));
    }

    protected Component box(Component component) {
        FlexBoxLayout box = new FlexBoxLayout();
        box.addClassNames(LumoStyles.Padding.Responsive.Horizontal.L, LumoStyles.Padding.Vertical.L);
        box.add(component);
        return box;
    }

    @Override
    protected String getTitle() {
        return "Сообщения: " + getBean().getName();
    }

}
