/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.field;

import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;

/**
 * @author Усов Андрей
 * @since 28/06/2019
 */
public class IntegerFieldCreator implements FieldCreator<Integer, Double, NumberField> {

    @Override
    public NumberField createField(String caption) {
        return new NumberField(caption);
    }

    @Override
    public <Bean> void bind(Binder.BindingBuilder<Bean, Double> bindingBuilder, ValueProvider<Bean, Integer> valueProvider, Setter<Bean, Integer> setter) {
        bindingBuilder.bind(t -> {
                    Integer value = valueProvider.apply(t);
                    return value != null ? new Double(value) : null;
                },
                (t, s) -> {
                    if (s != null) {
                        Integer value = s.intValue();
                        setter.accept(t, value);
                    }
                });
    }
}
