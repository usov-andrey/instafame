/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.tab;

import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.vaadin.custom.component.entity.EntityForm;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFormFieldsCreator;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Вкладка с полями
 *
 * @author Усов Андрей
 * @since 12/06/2019
 */
public class EntityTab<B extends IdHolder<?>> extends Tab<B> {

    private final EntityForm<B> form;

    public EntityTab(ServiceRef<? extends EntityWithIdService<B>> service) {
        this(new EntityForm<>(service.get().getBeanClass(), service));
    }

    public EntityTab(BinderFormFieldsCreator<B> binderFieldsCreator, ServiceRef<EntityWithIdService<B>> entityService) {
        this(new EntityForm<>(binderFieldsCreator, entityService));
    }

    public EntityTab(EntityForm<B> form) {
        this.form = form;
        draw();
    }

    @Override
    public void setParentBean(B bean) {
        super.setParentBean(bean);
        form.setBean(bean);
    }

    private void draw() {
        add(form);
        form.createFields();
    }

}
