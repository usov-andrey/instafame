/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import com.xalap.framework.utils.ReflectHelper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Date;

/**
 * Не может быть сериализовано из-за того, что хранит поле field
 *
 * @author Усов Андрей
 * @since 18/04/2019
 */
public class FieldAnnotationInfo {

    private final String name;
    private Field field;
    private final Method getter;
    private final Class<?> declaringClass;
    private final Class<?> type;
    private String key;

    private Method setter;
    private String caption;
    private String image;
    private boolean id = false;
    private Integer length;
    private boolean fk = false;//внешний ключ

    public FieldAnnotationInfo(Field field, Method getter) {
        this.field = field;
        this.getter = getter;
        setter(getter);
        name = field.getName();
        type = field.getType();
        declaringClass = field.getDeclaringClass();
    }

    public FieldAnnotationInfo(Method getter) {
        this.getter = getter;
        name = ReflectHelper.getGetterFieldName(getter);
        type = getter.getReturnType();
        declaringClass = getter.getDeclaringClass();
        setter(getter);
    }

    private void setter(Method getter) {
        ReflectHelper.getSetter(getter).ifPresent(set -> this.setter = set);
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key != null ? key : name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String caption() {
        return caption != null ? caption : getKey();
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isFk() {
        return fk;
    }

    public void setFk(boolean fk) {
        this.fk = fk;
    }

    public <B> String getStringValue(B bean) {
        Object value = getValue(bean);
        return value != null ? value.toString() : "";
    }

    public <B, T> T getValue(B bean) {
        return getter != null ? ReflectHelper.invokeMethodWithoutArg(bean, getter) : null;
    }

    public <B> void setValue(B bean, Object value) {
        if (setter != null) {
            ReflectHelper.invokeMethod(bean, setter, value);
        }
    }

    public boolean isString() {
        return String.class.isAssignableFrom(clazz());
    }

    private Class<?> clazz() {
        return type;
    }

    public Class<?> getType() {
        return type;
    }

    public boolean isLocalDate() {
        return LocalDate.class.isAssignableFrom(clazz());
    }

    public boolean isDate() {
        return Date.class.isAssignableFrom(clazz());
    }

    public boolean isIntOrInteger() {
        return Integer.class.isAssignableFrom(clazz()) || int.class.isAssignableFrom(clazz());
    }

    public boolean isDouble() {
        return Double.class.isAssignableFrom(clazz());
    }

    public boolean isEnum() {
        return Enum.class.isAssignableFrom(clazz());
    }

    /**
     * Указана или нет аннотация у поля или геттера
     */
    public boolean isAnnotationPresent(Class<? extends Annotation> aClass) {
        if (field != null && field.isAnnotationPresent(aClass)) {
            return true;
        }
        return getter.isAnnotationPresent(aClass);
    }

    public Class<?> getDeclaringClass() {
        return declaringClass;
    }

    public boolean isReadOnly() {
        return setter == null;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "FieldAnnotationInfo{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", field=" + field +
                ", getter=" + getter +
                ", setter=" + setter +
                ", declaringClass=" + declaringClass +
                ", type=" + type +
                ", caption='" + caption + '\'' +
                ", id=" + id +
                ", length=" + length +
                ", fk=" + fk +
                '}';
    }
}
