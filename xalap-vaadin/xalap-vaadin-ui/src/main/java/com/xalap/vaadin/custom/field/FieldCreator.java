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

/**
 * @author Усов Андрей
 * @since 28/06/2019
 */
public interface FieldCreator<ClassFieldType, FType, F extends Component & HasValue<?, FType>> {

    <Bean> void bind(Binder.BindingBuilder<Bean, FType> bindingBuilder, ValueProvider<Bean, ClassFieldType> valueProvider,
                     Setter<Bean, ClassFieldType> setter);

    F createField(String caption);

}
