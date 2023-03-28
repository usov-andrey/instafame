/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.layout;

import com.vaadin.flow.component.Component;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;
import com.xalap.vaadin.starter.ui.layout.size.Right;

/**
 * @author Усов Андрей
 * @since 06/08/2019
 */
public class FlexHorizontalLayout extends FlexBoxLayout {

    public FlexHorizontalLayout() {
        setFlexDirection(FlexDirection.ROW);
        setFlexWrap(FlexWrap.WRAP);
        setAlignItems(Alignment.CENTER);
        setSpacing(Right.S);
    }

    public static FlexHorizontalLayout of(Component... components) {
        FlexHorizontalLayout layout = new FlexHorizontalLayout();
        layout.add(components);
        return layout;
    }

    public FlexHorizontalLayout spacingM() {
        setSpacing(Right.M);
        return this;
    }

    public FlexHorizontalLayout spacingL() {
        setSpacing(Right.L);
        return this;
    }

    public FlexHorizontalLayout addComponents(Component... components) {
        add(components);
        return this;
    }

    public FlexHorizontalLayout top() {
        setAlignItems(Alignment.START);
        return this;
    }
}
