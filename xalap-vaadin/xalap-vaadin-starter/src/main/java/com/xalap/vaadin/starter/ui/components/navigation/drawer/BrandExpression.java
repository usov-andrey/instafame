/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.ui.components.navigation.drawer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.xalap.vaadin.starter.ui.util.UIUtils;

@CssImport("./styles/components/brand-expression.css")
public class BrandExpression extends Div {

    private final String CLASS_NAME = "brand-expression";

    protected Image logo;
    protected Label title;

    public BrandExpression(String text) {
        setClassName(CLASS_NAME);

        logo = new Image(UIUtils.IMG_PATH + "logos/18.png", "");
        logo.setAlt(text + " logo");
        logo.setClassName(CLASS_NAME + "__logo");

        title = UIUtils.createH3Label(text);
        title.addClassName(CLASS_NAME + "__title");

        add(logo, title);
    }

}
