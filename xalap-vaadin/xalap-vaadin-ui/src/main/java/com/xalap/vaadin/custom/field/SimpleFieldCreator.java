/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.field;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;

import java.util.function.Function;

/**
 * @author Усов Андрей
 * @since 28/06/2019
 */
public class SimpleFieldCreator<T, Field extends Component & HasValue<?, T>> implements FieldCreator<T, T, Field> {

    private final Function<String, Field> fieldSupplier;


    public SimpleFieldCreator(Function<String, Field> fieldSupplier) {
        this.fieldSupplier = fieldSupplier;
    }

    @Override
    public Field createField(String caption) {
        return fieldSupplier.apply(caption);
    }

    @Override
    public <Bean> void bind(Binder.BindingBuilder<Bean, T> bindingBuilder, ValueProvider<Bean, T> valueProvider, Setter<Bean, T> setter) {
        bindingBuilder.bind(valueProvider, setter);
    }
}
