/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.component;


import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.TextArea;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.ClipboardHelper;

/**
 * @author Usov Andrey
 * @since 2020-06-03
 */
public class ClipboardDialog extends Dialog {

    public ClipboardDialog(String text) {
        TextArea textArea = new TextArea();
        textArea.setValue(text);
        ButtonWithLog buffer = new ButtonWithLog("Скопировать в буффер", this::close);
        add(textArea, new ClipboardHelper(text, buffer));
    }

    public static void open(String text) {
        new ClipboardDialog(text).open();
    }
}
