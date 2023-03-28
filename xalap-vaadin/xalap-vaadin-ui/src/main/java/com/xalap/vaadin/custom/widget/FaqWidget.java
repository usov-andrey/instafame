/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.widget;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.starter.ui.util.FontWeight;
import com.xalap.vaadin.starter.ui.util.UIUtils;

/**
 * @author Usov Andrey
 * @since 2020-04-23
 */
public class FaqWidget extends Widget {

    private final VerticalLayout layout;

    public FaqWidget() {
        super("Часто задаваемые вопросы");
        layout = new CustomVerticalLayout();
        addBody(layout);
    }

    public FaqWidget add(String question, String answer) {
        Label h = new Label(question);
        UIUtils.setFontWeight(FontWeight.BOLDER, h);
        layout.add(new Details(h,
                new Label(answer)));
        return this;
    }
}
