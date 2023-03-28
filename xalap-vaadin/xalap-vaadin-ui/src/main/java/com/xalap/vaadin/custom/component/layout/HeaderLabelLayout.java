/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;

/**
 * @author Usov Andrey
 * @since 2020-05-08
 */
public class HeaderLabelLayout extends VerticalLayout {

    public HeaderLabelLayout(Label label, Component... components) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        label.addClassNames(LumoStyles.Margin.Bottom.M, LumoStyles.Margin.Top.XL);
        add(label);
        add(components);
    }

    public static HeaderLabelLayout h4Layout(String headerText, Component... components) {
        return new HeaderLabelLayout(UIUtils.createH4Label(headerText), components);
    }
}
