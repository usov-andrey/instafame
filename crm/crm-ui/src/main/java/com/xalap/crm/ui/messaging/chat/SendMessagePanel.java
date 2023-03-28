/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.chat;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.crm.service.contact.ContactDataBean;
import com.xalap.crm.service.contact.ContactDataType;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;
import lombok.val;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Панель с возможностью отправки сообщения на e-mail или в инстаграм
 *
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class SendMessagePanel extends VerticalLayout {

    private final ComboBox<ContactDataBean> contactDataComboBox = new ComboBox<>();//Адрес, куда отправлять сообщение

    public SendMessagePanel(InstagramDirectService instagramDirectService, MessageTemplateService messageTemplateService,
                            EmailService emailService,
                            Supplier<MessageThreadProvider> messageThreadProviderSupplier,
                            Consumer<MessageBean> newMessageConsumer, Runnable updateRunnable) {


        val sendEmailComponent = new SendEmailComponent(emailService, messageThreadProviderSupplier, newMessageConsumer);
        val sendInstagramMessageComponent = new SendInstagramMessageComponent(messageTemplateService,
                instagramDirectService, messageThreadProviderSupplier, newMessageConsumer, updateRunnable);

        contactDataComboBox.addValueChangeListener((HasValue.ValueChangeListener<AbstractField.ComponentValueChangeEvent<ComboBox<ContactDataBean>, ContactDataBean>>) event -> {
            ContactDataBean value = event.getValue();
            if (value != null) {
                if (value.getDataType() == ContactDataType.email) {
                    sendEmailComponent.setEmail(value.getValue());
                } else if (value.getDataType() == ContactDataType.instagram) {
                    sendInstagramMessageComponent.setInstagram(value.getValue());
                }
                sendEmailComponent.setVisible(value.getDataType() == ContactDataType.email);
                sendInstagramMessageComponent.setVisible(value.getDataType() == ContactDataType.instagram);
            }
        });
        contactDataComboBox.setItemLabelGenerator( item -> (item.getDataType() == ContactDataType.email ? "E-mail" : "Сообщение в direct") + ": " + item.getValue());
        contactDataComboBox.setWidth("200px");

        val layout = new HorizontalLayout();
        layout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        layout.add(new Label("Отправить"));
        layout.add(contactDataComboBox);
        add(layout);

        sendInstagramMessageComponent.setVisible(false);
        add(sendInstagramMessageComponent);

        sendEmailComponent.setVisible(false);
        add(sendEmailComponent);

        setPadding(false);
        setSpacing(false);
    }

    public void setContactDataList(List<ContactDataBean> contactDataList) {
        List<ContactDataBean> list = contactDataList.stream().filter(item -> item.getDataType() == ContactDataType.email
                || item.getDataType() == ContactDataType.instagram).collect(Collectors.toList());
        contactDataComboBox.setItems(list);
    }

}
