/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;

/**
 * @author Усов Андрей
 * @since 09/05/2019
 */
public class TextWithDialog extends Div {

    public TextWithDialog(String text) {
        super(new Label(text));
        Dialog dialog = new Dialog();
        dialog.add(new Label(text));
        dialog.setCloseOnOutsideClick(false);
        addClickListener(event -> dialog.open());
    }
}
