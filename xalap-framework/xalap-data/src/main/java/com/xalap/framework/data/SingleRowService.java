/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.ReflectHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * В базе хранится только одна строчка с данными
 * Используется, например, для хранения настроек
 *
 * @author Usov Andrey
 * @since 29/01/2020
 */
public class SingleRowService<T extends IdHolder<K>, R extends CrudRepository<T, K>, K> {

    private final Class<T> beanClass;
    private final K keyValue;
    @Autowired
    private R repository;

    @SuppressWarnings("unchecked")
    public SingleRowService(K keyValue) {
        beanClass = ReflectHelper.getGenericParameterClass(getClass(), SingleRowService.class, 0);
        this.keyValue = keyValue;
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public T get() {
        Optional<T> byId = repository.findById(keyValue);
        if (byId.isPresent()) {
            return byId.get();
        }
        T value = ReflectHelper.newInstance(beanClass);
        value.setId(keyValue);
        return value;
    }

    public T update(Consumer<T> consumer) {
        T bean = get();
        consumer.accept(bean);
        bean = repository.save(bean);
        if (!bean.getId().equals(keyValue)) {
            throw new IllegalStateException("Для поля id в бине " + beanClass +
                    " проставлен генератор ключа, например, @SequenceGenerator. Нужно убрать для корректной работы");
        }
        return bean;
    }

}
