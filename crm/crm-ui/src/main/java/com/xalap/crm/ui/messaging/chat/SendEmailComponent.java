/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.chat;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.xalap.crm.service.integration.sendgrid.templates.Template;
import com.xalap.crm.service.messaging.MessageBean;
import com.xalap.crm.service.messaging.email.EmailService;
import com.xalap.crm.service.messaging.thread.MessageThreadProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class SendEmailComponent extends HorizontalLayout {

    private String email;

    public SendEmailComponent(EmailService emailService, Supplier<MessageThreadProvider> messageThreadProviderSupplier,
                              Consumer<MessageBean> newMessageConsumer) {
        ComboBox<Template> emailTemplateComboBox = new ComboBox<>("", emailService.getActiveTemplates());
        emailTemplateComboBox.setRenderer(new ComponentRenderer<>(
                template -> new Label(template.getName())));
        emailTemplateComboBox.setItemLabelGenerator(Template::getName);
        Button sendEmailButton = new Button(VaadinIcon.PAPERPLANE_O.create());
        sendEmailButton.addClickListener(event -> {
            if (emailTemplateComboBox.getValue() != null) {
                MessageBean messageBean = emailService.sendMessage(messageThreadProviderSupplier.get(),
                        emailTemplateComboBox.getValue(), email, emailProcessor -> {
                        }, messageThreadProviderSupplier.get().getContactWithData().getInstagram());
                newMessageConsumer.accept(messageBean);
            }
        });
        //TODO ? А может и не нужен этот враппер
        setAlignItems(Alignment.CENTER);
        setWidth("100%");
        emailTemplateComboBox.setWidth("100%");
        emailTemplateComboBox.setPlaceholder("E-mail шаблон");
        sendEmailButton.setWidth("50px");
        HorizontalLayout inputBar = new HorizontalLayout(emailTemplateComboBox, sendEmailButton);
        inputBar.setWidth("100%");
        add(inputBar);
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
