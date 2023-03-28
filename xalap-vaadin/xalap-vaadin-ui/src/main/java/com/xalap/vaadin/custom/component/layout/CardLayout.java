/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.xalap.vaadin.custom.component.fluent.Div;
import com.xalap.vaadin.starter.ui.util.LumoStyles;

/**
 * @author Usov Andrey
 * @since 2020-05-08
 */
public class CardLayout extends FlexLayout {


    public CardLayout() {
        setJustifyContentMode(JustifyContentMode.CENTER);
        setFlexWrap(FlexWrap.WRAP);
        //В 14й версии Vaadin было так:
        //setWrapMode(WrapMode.WRAP);
    }

    public static CardLayout cards(Component... components) {
        return new CardLayout().addCards(components);
    }

    public CardLayout addCards(Component... components) {
        for (Component component : components) {
            Div div = new Div(component);
            div.addClassNames(LumoStyles.Padding.Horizontal.S, LumoStyles.Padding.Bottom.L);
            add(div);
        }
        return this;
    }
}
