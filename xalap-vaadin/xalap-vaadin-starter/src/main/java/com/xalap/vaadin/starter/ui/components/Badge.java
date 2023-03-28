/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.components;

import com.vaadin.flow.component.html.Span;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import com.xalap.vaadin.starter.ui.util.css.lumo.BadgeColor;
import com.xalap.vaadin.starter.ui.util.css.lumo.BadgeShape;
import com.xalap.vaadin.starter.ui.util.css.lumo.BadgeSize;

import java.util.StringJoiner;

public class Badge extends Span {

    public Badge(String text) {
        this(text, BadgeColor.NORMAL);
    }

    public Badge(String text, BadgeColor color) {
        super(text);
        UIUtils.setTheme(color.getThemeName(), this);
    }

    public Badge(String text, BadgeColor color, BadgeSize size, BadgeShape shape) {
        super(text);
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(color.getThemeName());
        if (shape.equals(BadgeShape.PILL)) {
            joiner.add(shape.getThemeName());
        }
        if (size.equals(BadgeSize.S)) {
            joiner.add(size.getThemeName());
        }
        UIUtils.setTheme(joiner.toString(), this);
    }

}
