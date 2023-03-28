/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.html.Label;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

/**
 * @author Усов Андрей
 * @since 11/06/2019
 */
public class LabelList extends CustomVerticalLayout {

    public LabelList addText(String caption) {
        add(new Label(caption));
        return this;
    }

    public LabelList clearAndAddText(String... caption) {
        removeAll();
        for (String s : caption) {
            addText(s);
        }
        return this;
    }
}
