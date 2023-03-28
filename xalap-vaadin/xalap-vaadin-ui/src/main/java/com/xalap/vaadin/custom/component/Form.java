/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

/**
 * Задаем как размещать компоненты внутри
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class Form extends CustomVerticalLayout {

    FormLayout formLayout = new FormLayout();

    public Form() {
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("800px", 2),
                new FormLayout.ResponsiveStep("1200px", 3));
        add(formLayout);
    }

    public Form addFields(Component... fields) {
        for (Component field : fields) {
            formLayout.add(field);
        }
        return this;
    }

    public void setButtons(Button... buttons) {
        HorizontalLayout layout = new HorizontalLayout();
        for (Button button : buttons) {
            layout.add(button);
        }
        add(layout);
    }

    protected void clearFields() {
        formLayout.removeAll();
    }
}
