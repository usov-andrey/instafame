/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.components;

import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;

public class Divider extends FlexBoxLayout implements HasSize, HasStyle {

    private final String CLASS_NAME = "divider";

    private final Div divider;

    public Divider(String height) {
        this(FlexComponent.Alignment.CENTER, height);
    }

    public Divider(FlexComponent.Alignment alignItems, String height) {
        setAlignItems(alignItems);
        setClassName(CLASS_NAME);
        setHeight(height);

        divider = new Div();
        UIUtils.setBackgroundColor(LumoStyles.Color.Contrast._10, divider);
        divider.setHeight("1px");
        divider.setWidthFull();
        add(divider);
    }

}
