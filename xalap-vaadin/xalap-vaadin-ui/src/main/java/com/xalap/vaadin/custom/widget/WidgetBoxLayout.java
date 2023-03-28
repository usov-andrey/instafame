/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.widget;

import com.vaadin.flow.component.Component;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;
import com.xalap.vaadin.starter.ui.layout.size.Horizontal;
import com.xalap.vaadin.starter.ui.layout.size.Top;
import com.xalap.vaadin.starter.ui.util.css.BoxSizing;
import com.xalap.vaadin.starter.ui.util.css.Display;

/**
 * Рамка для виджета
 *
 * @author Usov Andrey
 * @since 2020-04-23
 */
public class WidgetBoxLayout extends FlexBoxLayout {

    public WidgetBoxLayout(Component... components) {
        super(components);
        setBoxSizing(BoxSizing.BORDER_BOX);
        setDisplay(Display.BLOCK);
        setMargin(Top.L);
        setPadding(Horizontal.RESPONSIVE_L);
        setWidthFull();
    }
}
