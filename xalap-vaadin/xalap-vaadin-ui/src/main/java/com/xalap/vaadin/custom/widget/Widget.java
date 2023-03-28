/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.widget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.details.DetailsVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.xalap.vaadin.starter.ui.util.BoxShadowBorders;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import com.xalap.vaadin.starter.ui.util.css.BorderRadius;
import com.xalap.vaadin.starter.ui.util.css.Shadow;

/**
 * @author Usov Andrey
 * @since 2020-04-23
 */
public class Widget extends WidgetBoxLayout {

    private final Div bodyDiv;

    public Widget(String headerLabel) {
        super();
        Label header = UIUtils.createH3Label(headerLabel);
        header.addClassNames(LumoStyles.Padding.Left.M);

        bodyDiv = new Div();
        bodyDiv.addClassName(BoxShadowBorders.TOP);
        bodyDiv.addClassName(LumoStyles.Padding.Horizontal.M);

        Details card = new Details(header, bodyDiv);
        card.setOpened(true);
        card.addThemeVariants(DetailsVariant.REVERSE);

        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, card);
        UIUtils.setBorderRadius(BorderRadius.S, card);
        UIUtils.setShadow(Shadow.XS, card);

        add(card);
    }

    public Widget(String headerLabel, Component body) {
        this(headerLabel);
        addBody(body);
    }

    public void addBody(Component body) {
        bodyDiv.add(body);
    }


}
