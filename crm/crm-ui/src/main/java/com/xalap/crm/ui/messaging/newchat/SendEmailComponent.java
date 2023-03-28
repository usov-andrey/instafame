/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.newchat;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.xalap.crm.service.integration.sendgrid.templates.Template;

import java.util.List;
import java.util.function.Consumer;

/**
 * Форма отправки e-mail сообщения
 *
 * @author Usov Andrey
 * @since 30/01/2020
 */
public class SendEmailComponent extends HorizontalLayout {

    public SendEmailComponent(List<Template> templateList, Consumer<Template> sendEmailConsumer) {
        ComboBox<Template> emailTemplateComboBox = new ComboBox<>("", templateList);
        emailTemplateComboBox.setWidth("100%");
        emailTemplateComboBox.setPlaceholder("Выберите E-mail шаблон");
        emailTemplateComboBox.setItemLabelGenerator(Template::getName);
        emailTemplateComboBox.setRenderer(new ComponentRenderer<>(
                template -> {
                    return new Label(template.getName());
                }));

        Button sendEmailButton = new Button(VaadinIcon.PAPERPLANE_O.create());
        sendEmailButton.setWidth("50px");
        sendEmailButton.addClickListener(event -> {
            if (emailTemplateComboBox.getValue() != null) {
                sendEmailConsumer.accept(emailTemplateComboBox.getValue());
            }
        });
        setWidth("100%");
        setPadding(false);
        add(emailTemplateComboBox, sendEmailButton);
    }
}
