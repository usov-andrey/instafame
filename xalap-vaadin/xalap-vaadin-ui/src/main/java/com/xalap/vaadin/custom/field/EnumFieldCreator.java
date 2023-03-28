/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.field;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.component.EnumComboBox;

/**
 * @author Усов Андрей
 * @since 15/11/2019
 */
public class EnumFieldCreator<T extends Enum> extends SimpleFieldCreator<T, EnumComboBox<T>> {

    public EnumFieldCreator(Class<T> enumClass) {
        super(s -> new EnumComboBox<>(s, enumClass));
    }

    @Override
    public <Bean> void bind(Binder.BindingBuilder<Bean, T> bindingBuilder, ValueProvider<Bean, T> valueProvider, Setter<Bean, T> setter) {
        //bindingBuilder.asRequired();
        super.bind(bindingBuilder, valueProvider, setter);
    }
}
