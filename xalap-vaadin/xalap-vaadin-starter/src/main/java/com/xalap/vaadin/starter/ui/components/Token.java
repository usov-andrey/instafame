/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.xalap.vaadin.starter.ui.layout.size.Left;
import com.xalap.vaadin.starter.ui.layout.size.Right;
import com.xalap.vaadin.starter.ui.util.FontSize;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import com.xalap.vaadin.starter.ui.util.css.BorderRadius;
import com.xalap.vaadin.starter.ui.util.css.Display;

public class Token extends FlexBoxLayout {

    private final String CLASS_NAME = "token";

    public Token(String text) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setBackgroundColor(LumoStyles.Color.Primary._10);
        setBorderRadius(BorderRadius.M);
        setClassName(CLASS_NAME);
        setDisplay(Display.INLINE_FLEX);
        setPadding(Left.S, Right.XS);
        setSpacing(Right.XS);

        Label label = UIUtils.createLabel(FontSize.S, TextColor.BODY, text);
        Button button = UIUtils.createButton(VaadinIcon.CLOSE_SMALL, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY_INLINE);
        add(label, button);
    }

}
