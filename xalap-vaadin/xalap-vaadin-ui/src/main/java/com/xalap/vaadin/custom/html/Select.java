/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.html;

import com.vaadin.flow.component.*;
import com.vaadin.flow.shared.Registration;

/**
 * @author Usov Andrey
 * @since 2020-10-05
 */
@Tag("select")
public class Select extends HtmlContainer {

    public Select option(String value, String caption) {
        add(new Option(value, caption));
        return this;
    }

    @Synchronize("change")
    public String getValue() {
        return getElement().getProperty("value");
    }

    public void setValue(String value) {
        getElement().setProperty("value", value);
    }

    public Registration addChangeListener(ComponentEventListener<ChangeEvent> listener) {
        return this.addListener(ChangeEvent.class, listener);
    }

    @DomEvent("change")
    public static class ChangeEvent extends ComponentEvent<Select> {
        public ChangeEvent(Select source, boolean fromClient) {
            super(source, fromClient);
        }
    }

}
