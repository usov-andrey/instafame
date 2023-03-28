/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.spring;

import org.springframework.context.ApplicationContext;

/**
 * Используется для получения ссылки на бины спринга из статических методов
 * Может работать очень медленно, поэтому стоит использовать в крайнем случае
 *
 * @author Usov Andrey
 * @since 2020-02-14
 */
public class BeanFactory {

    static ApplicationContext context;

    private BeanFactory() {
    }

    public static <T> T get(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

}
