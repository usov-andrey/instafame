/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.xalap.vaadin.starter.ui.components.FlexBoxLayout;

import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 18/05/2019
 */
public class FieldWithButton<V, C extends AbstractField<C, V>> extends FlexBoxLayout {

    public FieldWithButton(AbstractField<C, V> field, String buttonText, Consumer<V> valueConsumer) {
        add(field);
        add(new Button(buttonText, event -> {
            valueConsumer.accept(field.getValue());
        }));
    }
}
