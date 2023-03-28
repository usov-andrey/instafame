/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.crud.binderfieldscreator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.HasPrefixAndSuffix;
import com.vaadin.flow.data.binder.Binder;
import com.xalap.vaadin.custom.field.FieldCreator;

/**
 * Выводит label перед полем
 *
 * @author Usov Andrey
 * @since 29/01/2020
 */
public class BinderHorizontalFieldsCreator<T> extends BinderFieldsCreator<T> {

    public BinderHorizontalFieldsCreator(Class<T> beanClass) {
        super(beanClass);
    }

    @Override
    protected <ClassFieldType, FType, F extends Component & HasValue<?, FType> & HasSize> Binder.BindingBuilder<T, FType>
    bindingBuilder(FieldCreator<ClassFieldType, FType, F> fieldCreator, String caption, String image) {


        //Создаем поле с пустым caption
        F field = fieldCreator.createField("");
        if (image != null && field instanceof HasPrefixAndSuffix) {
            ((HasPrefixAndSuffix) field).setPrefixComponent(VaadinIcon.valueOf(image).create());
        } else if (!caption.isEmpty()) {
            layout.add(new Label(caption));
        }
        field.setWidth("150px");

        layout.add(field);
        //F field = fieldCreator.createField("");
        //layout.add(new CompositeField<>(field, caption));
        //layout.addFormItem(field, caption);
        return binder.forField(field);
    }

}
