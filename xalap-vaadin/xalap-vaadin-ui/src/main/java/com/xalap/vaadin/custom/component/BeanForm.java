/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.data.binder.Binder;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFormFieldsCreator;

import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 07/06/2019
 */
public class BeanForm<B extends Serializable> extends Form {

    private final Binder<B> binder = new Binder<>();
    private B bean;
    private BinderFormFieldsCreator<B> binderFieldsCreator;

    public BeanForm(BinderFormFieldsCreator<B> binderFieldsCreator) {
        setFieldProvider(binderFieldsCreator);

    }

    public void setFieldProvider(BinderFormFieldsCreator<B> binderFieldsCreator) {
        this.binderFieldsCreator = binderFieldsCreator;
        binderFieldsCreator.setLayout(formLayout);
        binderFieldsCreator.setBinder(binder);
    }

    protected BinderFormFieldsCreator<B> fieldsCreator() {
        return binderFieldsCreator;
    }

    /**
     * Отрисовываем поля
     */
    public void createFields() {
        binderFieldsCreator.addFields();
    }

    public B getBean() {
        return bean;
    }

    /**
     * Если нужно отрисовать что-то в зависимости от B, то перекрываем этот метод
     */
    public void setBean(B bean) {
        this.bean = bean;
        binder.setBean(bean);
    }
}
