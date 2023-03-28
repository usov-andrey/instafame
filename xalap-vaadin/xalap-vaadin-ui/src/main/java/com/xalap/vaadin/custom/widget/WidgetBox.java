/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.widget;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import com.xalap.vaadin.starter.ui.util.css.BorderRadius;
import com.xalap.vaadin.starter.ui.util.css.Shadow;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class WidgetBox extends WidgetBoxLayout {

    private final Div bodyDiv;

    public WidgetBox(Component... components) {
        bodyDiv = new Div();
        UIUtils.setBackgroundColor(LumoStyles.Color.BASE_COLOR, bodyDiv);
        UIUtils.setBorderRadius(BorderRadius.S, bodyDiv);
        UIUtils.setShadow(Shadow.XS, bodyDiv);

        add(bodyDiv);
        for (Component component : components) {
            addBody(component);
        }
    }

    public WidgetBox padding() {
        bodyDiv.addClassName(LumoStyles.Padding.Wide.XL);
        return this;
    }

    public void addBody(Component body) {
        bodyDiv.add(body);
    }
}
