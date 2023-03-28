/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.crud.binderfieldscreator;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.xalap.framework.domain.annotation.BeanAnnotationInfo;
import com.xalap.framework.domain.annotation.BeanAnnotationInfoProvider;
import com.xalap.framework.domain.annotation.FieldAnnotationInfo;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.spring.BeanFactory;
import com.xalap.vaadin.custom.field.EnumFieldCreator;
import com.xalap.vaadin.custom.field.FieldCreator;
import com.xalap.vaadin.custom.field.FieldRegistry;
import com.xalap.vaadin.custom.field.SimpleFieldCreator;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Размещает компоненты полей из класса beanClass на layout
 * и связывает их с binder
 *
 * @author Усов Андрей
 * @since 09/04/2019
 */
public class BinderFieldsCreator<T> implements HasLog, Serializable {

    protected Class<T> beanClass;
    protected Binder<T> binder;
    protected HasComponents layout;
    private boolean defaultFields = true;//Добавлять поля по-умолчанию
    private boolean withCaptions = true;

    public BinderFieldsCreator(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    private BeanAnnotationInfo getInfo() {
        return BeanFactory.get(BeanAnnotationInfoProvider.class).getInfo(beanClass);
    }

    public void setBinder(Binder<T> binder) {
        this.binder = binder;
    }

    public void setLayout(HasComponents layout) {
        this.layout = layout;
    }

    public final void addFields() {
        if (defaultFields) {
            getInfo().getFields().stream().filter(fieldAnnotationInfo -> !fieldAnnotationInfo.isId())
                    .forEach(this::bindField);
        }
    }

    public BinderFieldsCreator<T> addFields(String... fieldNames) {
        BeanAnnotationInfo info = getInfo();
        for (String fieldName : fieldNames) {
            bindField(info.getField(fieldName));
        }
        return this;
    }

    public <F> F addField(String fieldName) {
        Binder.BindingBuilder<T, ?> bindingBuilder = bindField(getInfo().getField(fieldName));
        if (bindingBuilder == null) {
            throw new IllegalArgumentException("Not found bindingBuilder for fieldName:" + fieldName);
        }
        return (F) bindingBuilder.getField();
    }

    private FieldCreator<?, ?, ?> getFieldCreator(FieldAnnotationInfo fieldAnnotationInfo) {
        if (fieldAnnotationInfo.isString()) {
            Integer length = fieldAnnotationInfo.getLength();
            return new SimpleFieldCreator<>(length != null && length > 255 ? TextArea::new : TextField::new);
        } else if (fieldAnnotationInfo.isEnum()) {
            Class<? extends Enum> enumClass = (Class<? extends Enum>) fieldAnnotationInfo.getType();
            return new EnumFieldCreator<>(enumClass);
        }
        return BeanFactory.get(FieldRegistry.class).get(fieldAnnotationInfo);
    }

    private <ClassFieldType, FType, F extends Component & HasValue<?, FType> & HasSize> Binder.BindingBuilder<T, FType> bindField(FieldAnnotationInfo fieldAnnotationInfo) {
        String caption = withCaptions ? fieldAnnotationInfo.caption() : "";
        FieldCreator<ClassFieldType, FType, F> fieldCreator = (FieldCreator<ClassFieldType, FType, F>) getFieldCreator(fieldAnnotationInfo);
        if (fieldCreator != null) {
            Binder.BindingBuilder<T, FType> bindingBuilder = bindingBuilder(fieldCreator, caption, fieldAnnotationInfo.getImage());
            if (fieldAnnotationInfo.isAnnotationPresent(NotNull.class)) {
                bindingBuilder.asRequired();
            }
            bindingBuilder.getField().setReadOnly(fieldAnnotationInfo.isReadOnly());
            fieldCreator.bind(bindingBuilder, fieldAnnotationInfo::getValue,
                    fieldAnnotationInfo::setValue);
            return bindingBuilder;
        } else {
            log().info("Not found fieldCreator for {}", fieldAnnotationInfo);
        }
        return null;
    }

    protected <ClassFieldType, FType, F extends Component & HasValue<?, FType> & HasSize> Binder.BindingBuilder<T, FType>
    bindingBuilder(FieldCreator<ClassFieldType, FType, F> fieldCreator, String caption, String image) {
        F field = fieldCreator.createField(caption);
        return addField(field);
    }

    public <FIELDVALUE, F extends Component & HasValue<?, FIELDVALUE>> Binder.BindingBuilder<T, FIELDVALUE> addField(F field) {
        layout.add(field);
        return binder.forField(field);
    }

    public boolean isValidationOk() {
        return binder.validate().isOk();
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public BinderFieldsCreator<T> noLabels() {
        withCaptions = false;
        return this;
    }

    public BinderFieldsCreator<T> noDefaultFields() {
        defaultFields = false;
        return this;
    }
}
