/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.components;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.xalap.vaadin.starter.ui.util.FontSize;
import com.xalap.vaadin.starter.ui.util.FontWeight;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import com.xalap.vaadin.starter.ui.util.css.BorderRadius;

public class Initials extends FlexBoxLayout {

    private final String CLASS_NAME = "initials";

    public Initials(String initials) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setBackgroundColor(LumoStyles.Color.Contrast._10);
        setBorderRadius(BorderRadius.L);
        setClassName(CLASS_NAME);
        UIUtils.setFontSize(FontSize.S, this);
        UIUtils.setFontWeight(FontWeight._600, this);
        setHeight(LumoStyles.Size.M);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setWidth(LumoStyles.Size.M);

        add(initials);
    }

}
