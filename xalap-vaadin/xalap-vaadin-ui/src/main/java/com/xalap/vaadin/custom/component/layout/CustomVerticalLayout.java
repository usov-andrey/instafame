/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * @author Усов Андрей
 * @since 21/06/2019
 */
public class CustomVerticalLayout extends VerticalLayout {

    public CustomVerticalLayout() {
        setSpacing(false);
        setPadding(false);
    }

    public static CustomVerticalLayout of(Component... components) {
        CustomVerticalLayout layout = new CustomVerticalLayout();
        layout.add(components);
        return layout;
    }

    public CustomVerticalLayout padding() {
        setPadding(true);
        return this;
    }

    public CustomVerticalLayout spacing() {
        setSpacing(true);
        return this;
    }

    public CustomVerticalLayout center() {
        setAlignItems(Alignment.CENTER);
        return this;
    }
}
