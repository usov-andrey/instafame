/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.chat;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.direct.InstagramDirectService;
import com.xalap.crm.service.messaging.template.MessageTemplateBean;
import com.xalap.crm.service.messaging.template.MessageTemplateService;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;
import com.xalap.framework.exception.RunnableWithServiceException;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class SendInstagramMessageComponent extends CustomVerticalLayout {

    private String instagram;

    public SendInstagramMessageComponent(MessageTemplateService messageTemplateService,
                                         InstagramDirectService instagramDirectService,
                                         Supplier<MessageThreadProvider> messageThreadProviderSupplier,
                                         Consumer<MessageBean> newMessageConsumer, Runnable updateRunnable) {
        TextArea textField = new TextArea();
        //Вставляем шаблон
        ComboBox<MessageTemplateBean> templateBeanComboBox = new ComboBox<>("");
        templateBeanComboBox.setItems(messageTemplateService.getAll());
        templateBeanComboBox.addValueChangeListener(comboBox -> {
            MessageTemplateBean value = comboBox.getValue();
            if (value != null) {
                textField.setValue(messageTemplateService.process(templateBeanComboBox.getValue(),
                        messageThreadProviderSupplier.get().getContactWithData()));
            }
        });
        templateBeanComboBox.setWidth("100%");
        templateBeanComboBox.setPlaceholder("Использовать шаблон");
        add(templateBeanComboBox);


        HorizontalLayout textLayout = new HorizontalLayout();
        add(textLayout);
        textLayout.setWidthFull();

        textField.setWidth("1000%");
        textLayout.add(textField);

        Button sendButton = new Button(VaadinIcon.PAPERPLANE_O.create());
        sendButton.addClickListener(event -> {
            if (StringHelper.isNotEmpty(textField.getValue())) {
                MessageBean messageBean = instagramDirectService.sendMessage(messageThreadProviderSupplier.get().getContact().getId(),
                        instagram, textField.getValue());
                newMessageConsumer.accept(messageBean);

                textField.setValue("");
            }
        });
        textLayout.add(sendButton);

        Button updateButton = new Button(VaadinIcon.REFRESH.create());
        updateButton.addClickListener(event -> {
            RunnableWithServiceException.run(() -> instagramDirectService.updateThreadMessages(instagram));
            updateRunnable.run();
        });
        textLayout.add(updateButton);

        setWidthFull();

    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
