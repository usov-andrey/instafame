/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.chat;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.MessageType;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateBean;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadBean;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;
import com.xalap.crm.service.messaging.thread.MessageThreadService;
import com.xalap.framework.utils.DateHelper;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.List;

/**
 * Чат
 *
 * @author Усов Андрей
 * @since 04/06/2019
 */
public class ChatComponent extends VerticalLayout {

    private final ServiceRef<MessageService> messageService;
    private final ServiceRef<MessageThreadService> messageThreadService;
    private final ServiceRef<MessageTemplateService> messageTemplateService;
    private MessageThreadProvider messageThreadProvider;
    private final SendMessagePanel sendPanel;

    public ChatComponent(ServiceRef<MessageThreadService> messageThreadService, ServiceRef<MessageService> messageService,
                         ServiceRef<InstagramDirectService> instagramDirectService, ServiceRef<MessageTemplateService> messageTemplateService,
                         ServiceRef<EmailService> emailService) {
        this.messageService = messageService;
        this.messageThreadService = messageThreadService;
        this.messageTemplateService = messageTemplateService;

        UI.getCurrent().getPage().addStyleSheet("frontend://styles/messaging.css");
        setSizeFull();

        sendPanel = new SendMessagePanel(instagramDirectService.get(), messageTemplateService.get(), emailService.get(),
                () -> messageThreadProvider,
                this::addMessage,
                this::reloadMessages);
    }

    public void setCurrentThread(MessageThreadBean currentThread) {
        setMessageThreadProvider(messageThreadService.get().messageThreadProvider(currentThread));
    }

    public void setContact(ContactBean contact) {
        setMessageThreadProvider(messageThreadService.get().messageThreadProvider(contact));
    }

    public void addMessage(MessageBean messageBean) {
        var messageView = new Div();
        add(messageView);

        setup(messageView, messageBean);
    }

    private void setup(Div messageView, MessageBean messageBean) {
        messageView.addClassName("message");
        if (messageBean.getMessageType() == MessageType.outbound) messageView.addClassName("friend-message");
        else
            messageView.addClassName(messageBean.getMessageType() == MessageType.sending ? "planned-message" : "user-message");
        messageView.add(new Label(messageBean.getText() + " (" + DateHelper.getDateTime(messageBean.getCreateTime()) + ")"));
        messageView.add(new Button("Добавить в шаблоны", buttonClickEvent -> {
            var bean = new MessageTemplateBean();
            bean.setName("Создано из чата:" + messageBean.getThread().getContact().getName());
            bean.setText(messageBean.getText());
            messageTemplateService.get().save(bean);
        }));
        if (messageBean.getMessageType() != MessageType.inbound) {
            messageView.getStyle().set("align-self", "flex-end");
        }
    }

    public Component getMessageWindow() {
        return this;
    }

    /**
     * Загружаем сообщения в чат
     */
    private void setMessageThreadProvider(MessageThreadProvider messageThreadProvider) {
        this.messageThreadProvider = messageThreadProvider;
        reloadMessages();
        //Адреса контактов
        sendPanel.setContactDataList(messageThreadProvider.getContactWithData().getContactDataList());
    }

    private void reloadMessages() {
        MessageThreadBean bean = messageThreadProvider.getBean();
        if (bean != null) {
            //Загружаем сообщения
            List<MessageBean> messages = messageService.get().getMessagesSorted(bean);
            removeAll();
            messages.forEach(this::addMessage);
        }
    }

    public Component getInputBar() {
        return sendPanel;
    }
}
