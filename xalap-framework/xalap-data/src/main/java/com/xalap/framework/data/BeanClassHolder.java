/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data;

import com.xalap.framework.utils.ReflectHelper;

/**
 * Вычисляет по первому типизованному параметру class и его сохраняет
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public abstract class BeanClassHolder<T> {

    private final Class<T> beanClass;

    protected BeanClassHolder(Class<?> superClass) {
        beanClass = ReflectHelper.getGenericParameterClass(getClass(), superClass, 0);
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }
}
