/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.chat;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageType;
import com.xalap.framework.utils.DateHelper;

import java.util.List;

/**
 * Отображение списка сообщений
 *
 * @author Usov Andrey
 * @since 30/01/2020
 */
public class MessageListComponent extends VerticalLayout {

    public MessageListComponent(List<MessageBean> messages) {
        UI.getCurrent().getPage().addStyleSheet("frontend://styles/messaging.css");
        setSizeFull();
        messages.forEach(this::addMessage);
    }

    public Div addMessage(MessageBean messageBean) {
        Div messageView = new Div();
        add(messageView);

        setup(messageView, messageBean);
        return messageView;
    }

    private void setup(Div messageView, MessageBean messageBean) {
        messageView.addClassName("message");
        messageView.addClassName(messageBean.getMessageType() == MessageType.outbound ? "friend-message" :
                messageBean.getMessageType() == MessageType.sending ? "planned-message" : "user-message");
        messageView.add(new Label(messageBean.getText() + " (" + DateHelper.getDateTime(messageBean.getCreateTime()) + ")"));
        /*
        messageView.add(new Button("Добавить в шаблоны", new ComponentEventListener<ClickEvent<Button>>() {
            @Override
            public void onComponentEvent(ClickEvent<Button> buttonClickEvent) {
                MessageTemplateBean bean = new MessageTemplateBean();
                bean.setName("Создано из чата:" + messageBean.getThread().getContact().getName());
                bean.setText(messageBean.getText());
                messageTemplateService.save(bean);
            }
        }));*/
        if (messageBean.getMessageType() != MessageType.inbound) {
            messageView.getStyle().set("align-self", "flex-end");
        }
    }
}
