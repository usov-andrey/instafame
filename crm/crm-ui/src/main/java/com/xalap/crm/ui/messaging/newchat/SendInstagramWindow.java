/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.messaging.newchat;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.xalap.crm.service.messaging.template.MessageTemplateBean;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Окно отправки сообщения в инстаграм
 *
 * @author Usov Andrey
 * @since 30/01/2020
 */
public class SendInstagramWindow extends CustomVerticalLayout {

    public SendInstagramWindow(Collection<MessageTemplateBean> messageTemplateList,
                               Function<MessageTemplateBean, String> applyTemplateFunction, Consumer<String> sender) {
        TextArea textField = new TextArea();
        //Вставляем шаблон
        ComboBox<MessageTemplateBean> templateBeanComboBox = new ComboBox<>("");
        templateBeanComboBox.setItems(messageTemplateList);
        templateBeanComboBox.addValueChangeListener(comboBox -> {
            MessageTemplateBean value = comboBox.getValue();
            if (value != null) {
                textField.setValue(applyTemplateFunction.apply(value));
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
                sender.accept(textField.getValue());
                textField.setValue("");
            }
        });
        textLayout.add(sendButton);
        /*
        Button updateButton = new Button(VaadinIcon.REFRESH.create());
        updateButton.addClickListener(event -> {
            instagramDirectService.getInstagramDirectAPI().updateMessages(messageThreadProviderSupplier.get().getContact().getId(), instagram, updateRunnable);
        });
        textLayout.add(updateButton);*/

        setWidthFull();

    }
}
