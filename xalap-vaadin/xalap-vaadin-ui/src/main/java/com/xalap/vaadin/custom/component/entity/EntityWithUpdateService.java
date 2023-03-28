/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.entity;

import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.WithId;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Usov Andrey
 * @since 2020-02-14
 */
public class EntityWithUpdateService<B extends WithId> implements EntityWithIdService<B> {

    private final ServiceRef<? extends EntityWithIdService<B>> entityService;
    private final Consumer<B> afterOperationConsumer;

    public EntityWithUpdateService(ServiceRef<? extends EntityWithIdService<B>> entityService, Consumer<B> afterOperationConsumer) {
        this.entityService = entityService;
        this.afterOperationConsumer = afterOperationConsumer;
    }

    @Override
    public B save(B bean) {
        B result = entityService.get().save(bean);
        afterOperationConsumer.accept(bean);
        return result;
    }

    @Override
    public void delete(B bean) {
        entityService.get().delete(bean);
        afterOperationConsumer.accept(bean);
    }

    @Override
    public void copy(B bean) {
        entityService.get().copy(bean);
        afterOperationConsumer.accept(bean);
    }

    @Override
    public List<B> getAll() {
        return entityService.get().getAll();
    }

    @Override
    public Class<B> getBeanClass() {
        return entityService.get().getBeanClass();
    }

    @Override
    public B createNew() {
        return entityService.get().createNew();
    }
}
