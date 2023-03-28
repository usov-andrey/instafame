/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.xalap.framework.utils.StringHelper;
import com.xalap.vaadin.starter.ui.util.UIUtils;

/**
 * @author Usov Andrey
 * @since 2020-04-30
 */
public class SimpleCard extends CustomVerticalLayout {

    public SimpleCard add(String label, String value) {
        return add(label, new Label(value));
    }

    public SimpleCard add(String label, Double value) {
        return add(label, StringHelper.toString(value));
    }

    public SimpleCard addMoney(String label, Double value) {
        return add(label, value != null ? UIUtils.createAmountLabel(value) : new Label());
    }

    public SimpleCard add(String label, Component component) {
        add(FlexHorizontalLayout.of(new Label(label + ": "), component));
        return this;
    }
}
