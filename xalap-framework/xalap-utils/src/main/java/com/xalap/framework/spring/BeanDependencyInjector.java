/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.spring;

import com.xalap.framework.utils.ReflectHelper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Использовать, если нужно подставить зависимости в какой-то уже созданный обьект
 * Подстанока зависимостей может выполнятся очень медленно, поэтому стоит использовать только
 * в крайнем случае
 *
 * @author Usov Andrey
 * @since 2020-02-14
 */
@Service
public class BeanDependencyInjector implements ApplicationContextAware {

    private ApplicationContext appContext;

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
        BeanFactory.context = applicationContext;
    }

    /**
     * Подставляем зависимости spring в обьект
     */
    public <T> void inject(T instance) {
        if (appContext.getParent() != null) {
            appContext.getParent().getAutowireCapableBeanFactory().autowireBean(instance);
        }
        appContext.getAutowireCapableBeanFactory().autowireBean(instance);
    }

    /**
     * Создаем обьект и подставляем зависимости 
     */
    public <T> T create(Class<T> clazz) {
        T object = ReflectHelper.newInstance(clazz);
        inject(object);
        return object;
    }

    public <T> T create(String className) {
        return create(ReflectHelper.classForName(className));
    }

}
