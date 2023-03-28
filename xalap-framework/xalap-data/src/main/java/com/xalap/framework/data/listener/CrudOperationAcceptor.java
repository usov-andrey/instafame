/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.listener;

import com.xalap.framework.domain.holder.IdHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Усов Андрей
 * @since 10/06/2019
 */
@Service
public class CrudOperationAcceptor {

    private final Set<Class> beanClasses = new HashSet<>();
    private final Set<CrudOperation> operations = new HashSet<>();
    private final Set<CrudOperationStage> stages = new HashSet<>();
    private boolean excludeAll = true;//По-умолчанию все исключяем и включаем только то, что задано

    public CrudOperationAcceptor includeAll() {
        excludeAll = false;
        return this;
    }

    public CrudOperationAcceptor include(Class... classes) {
        Collections.addAll(beanClasses, classes);
        return this;
    }

    public CrudOperationAcceptor include(CrudOperationStage... stages) {
        Collections.addAll(this.stages, stages);
        return this;
    }

    public CrudOperationAcceptor include(CrudOperation... operations) {
        Collections.addAll(this.operations, operations);
        return this;
    }

    public CrudOperationAcceptor exclude(Class... classes) {
        return include(classes);
    }

    public CrudOperationAcceptor exclude(CrudOperationStage... stages) {
        return include(stages);
    }

    public CrudOperationAcceptor exclude(CrudOperation... operations) {
        return include(operations);
    }

    public <T extends IdHolder<Id>, Id> boolean accept(CrudOperationStage stage, CrudOperation operation, T bean) {
        if (excludeAll) {
            return beanClasses.contains(bean.getClass()) && stages.contains(stage) && operations.contains(operation);
        } else {
            return !beanClasses.contains(bean.getClass()) && !stages.contains(stage) && !operations.contains(operation);
        }
    }
}
