/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data;

import com.xalap.framework.data.listener.CrudOperation;
import com.xalap.framework.data.listener.CrudOperationListener;
import com.xalap.framework.data.listener.CrudOperationStage;
import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.spring.BeanFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 10/06/2019
 */
@Service
public class CrudListenersService {

    private final Map<Class, CrudService> crudServiceMap = new HashMap<>();
    private final List<CrudOperationListener> listeners = new ArrayList<>();

    public static CrudListenersService getInstance() {
        return BeanFactory.get(CrudListenersService.class);
    }

    public void addListener(CrudOperationListener listener) {
        listeners.add(listener);
    }

    public void addCrudService(Class beanClass, CrudService crudService) {
        crudServiceMap.put(beanClass, crudService);
    }

    public <T extends IdHolder<Id>, R extends PageableRepository<T, Id>, Id> CrudService<T, R, Id> getCrudService(Class<T> beanClass) {
        return crudServiceMap.get(beanClass);
    }

    private <T extends IdHolder> void notify(Consumer<CrudOperationListener> consumer) {
        for (CrudOperationListener listener : listeners) {
            consumer.accept(listener);
        }
    }

    public <T extends IdHolder<Id>, Id  > void before(CrudOperation operation, T bean) {
        notify(listener -> listener.onEvent(CrudOperationStage.before, operation, bean));
    }

    public <T extends IdHolder<Id>, Id  > void after(CrudOperation operation, T bean) {
        notify(listener -> listener.onEvent(CrudOperationStage.after, operation, bean));
    }
}
