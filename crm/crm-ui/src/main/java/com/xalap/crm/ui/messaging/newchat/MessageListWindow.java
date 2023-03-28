/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.newchat;

import com.vaadin.flow.component.Component;
import com.xalap.crm.service.contact.ContactDataBean;
import com.xalap.crm.service.contact.ContactDataType;
import com.xalap.crm.service.contact.ContactWithDataBean;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.MessageService;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;
import com.xalap.crm.ui.messaging.chat.MessageListComponent;
import com.xalap.vaadin.custom.component.DetailsWindow;
import com.xalap.vaadin.custom.component.detailsdrawer.EmptyDetailsDrawerFooter;
import com.xalap.vaadin.custom.tab.TabListComponent;
import com.xalap.vaadin.starter.ui.components.detailsdrawer.DetailsDrawer;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Отображает окно с сообщениями и панелью для отправки сообщений
 *
 * @author Usov Andrey
 * @since 30/01/2020
 */
public class MessageListWindow extends DetailsWindow<MessageThreadProvider> {

    public MessageListWindow(
            ServiceRef<MessageService> messageService, ServiceRef<EmailService> emailService,
            ServiceRef<MessageTemplateService> messageTemplateService, ServiceRef<InstagramDirectService> instagramDirectService) {

        addBeanListener(value -> {
            detailsDrawerHeader.setTitle(value.getContact().getName());
            //Отображаем список сообщений
            List<MessageBean> messages = value.getBean() != null ? messageService.get().getMessagesSorted(value.getBean())
                    : new ArrayList<>();
            MessageListComponent messageListComponent = new MessageListComponent(messages);
            detailsDrawer.setContent(messageListComponent);

            TabListComponent<Component> tabListComponent = new TabListComponent<>();
            tabListComponent.draw();
            setFooter(tabListComponent);

            ContactWithDataBean contactWithData = value.getContactWithData();
            List<ContactDataBean> contactDataList = contactWithData.getContactDataList();
            List<ContactDataBean> list = contactDataList.stream().filter(item -> item.getDataType() == ContactDataType.email
                    || item.getDataType() == ContactDataType.instagram).collect(Collectors.toList());
            for (ContactDataBean dataBean : list) {
                tabListComponent.createTab(dataBean.getDataType().name() + ":" + dataBean.getValue(),
                        dataBean.getDataType() == ContactDataType.email ?
                                new SendEmailComponent(emailService.get().getActiveTemplates(), template -> {
                                    //Отправить e-mail
                                    MessageBean messageBean = emailService.get().sendMessage(value,
                                            template, dataBean.getValue(), emailProcessor -> {
                                            }, contactWithData.getInstagram());
                                    messageListComponent.addMessage(messageBean);
                                })
                                :
                                new SendInstagramWindow(messageTemplateService.get().getAll(),
                                        bean -> messageTemplateService.get().process(bean, contactWithData), //Получение текста по шаблону
                                        messageText -> {
                                            MessageBean messageBean = instagramDirectService.get().sendMessage(//Отправка
                                                    contactWithData.getContact().getId(), dataBean.getValue(),
                                                    messageText);
                                            messageListComponent.addMessage(messageBean);
                                        }

                                )
                );


            }
        });
    }

    private void setFooter(TabListComponent<?> tabListComponent) {
        detailsDrawerFooter = new EmptyDetailsDrawerFooter();
        detailsDrawer.setFooter(detailsDrawerFooter);
        detailsDrawerFooter.add(tabListComponent);
    }

    @Override
    protected DetailsDrawer newDetailsDrawer() {
        return new DetailsDrawer(DetailsDrawer.Position.RIGHT);
    }


}
