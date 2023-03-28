/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.ReflectHelper;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Сортировка должна быть на сервере
 *
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class BeanWithIdGridPanel<B extends IdHolder<ID>, ID extends Comparable<ID>> extends GridPanel<B> {

    public BeanWithIdGridPanel(Class<B> beanClass) {
        super(beanClass);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <B extends Serializable> Function<Class<?>, GridPanel<B>> createBeanDefault(Class<?> parentClass) {
        return aClass -> {
            Class<B> beanClass = ReflectHelper.getGenericParameterClass(aClass, parentClass, 0);
            return new BeanWithIdGridPanel(beanClass).sortWithId();
        };
    }

    public BeanWithIdGridPanel<B, ID> sortWithId() {
        dataSource().setSortComparator((o1, o2) -> o2.getId().compareTo(o1.getId()));
        return this;
    }

}
