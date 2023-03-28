/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.detailsdrawer;

import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;
import com.xalap.vaadin.starter.ui.layout.size.Horizontal;
import com.xalap.vaadin.starter.ui.layout.size.Right;
import com.xalap.vaadin.starter.ui.layout.size.Vertical;
import com.xalap.vaadin.starter.ui.util.LumoStyles;

/**
 * Стандартный DetailsDrawerFooter содержит кнопки Save и Cancel
 *
 * @author Usov Andrey
 * @since 2020-02-13
 */
public class EmptyDetailsDrawerFooter extends FlexBoxLayout {

    public EmptyDetailsDrawerFooter() {
        setBackgroundColor(LumoStyles.Color.Contrast._5);
        setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
        setSpacing(Right.S);
        setWidthFull();
    }

}
