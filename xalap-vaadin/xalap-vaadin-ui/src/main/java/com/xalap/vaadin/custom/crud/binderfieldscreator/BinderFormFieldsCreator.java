/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.crud.binderfieldscreator;

/**
 * Добалвяем поля на FormLayout
 *
 * @author Usov Andrey
 * @since 29/01/2020
 */
public class BinderFormFieldsCreator<T> extends BinderFieldsCreator<T> {

    public BinderFormFieldsCreator(Class<T> beanClass) {
        super(beanClass);
    }

    @Override
    public BinderFormFieldsCreator<T> noDefaultFields() {
        super.noDefaultFields();
        return this;
    }
}
