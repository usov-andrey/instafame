/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.contact;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataService;
import com.xalap.crm.service.contact.ContactService;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.ui.contact.duplicate.ContactJoinTab;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 15/06/2019
 */
@Route(value = ContactListFrame.VIEW_NAME, layout = MainLayout.class)
public class ContactFrame extends RootEntityFrame<ContactBean, Integer> {

    @Autowired
    public ContactFrame(ServiceRef<ContactService> service, ServiceRef<ContactDataService> contactDataService, ServiceRef<OrderService> orderService,
                        ServiceRef<QuizService> quizService, ServiceRef<LeadService> leadService,
                        ServiceRef<MessageThreadService> messageThreadService, ServiceRef<MessageService> messageService,
                        ServiceRef<EmailService> emailService,
                        ServiceRef<MessageTemplateService> messageTemplateService,
                        ServiceRef<InstagramDirectService> instagramDirectService) {
        super(service, ContactListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
                .addTab("Контактные данные", new ContactDataListTab(this, contactDataService))
                .addTab("Заказы", new ContactOrderListTab(this, orderService))
                .addTab("Заявки", new ContactLeadListTab(leadService, quizService, orderService))
                .addTab("Сообщения", new ContactMessageListTab(messageThreadService, messageService, emailService,
                        messageTemplateService, instagramDirectService))
                .addTab("Обьединение контакта", new ContactJoinTab(service, contactDataService))
        );
    }


    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
