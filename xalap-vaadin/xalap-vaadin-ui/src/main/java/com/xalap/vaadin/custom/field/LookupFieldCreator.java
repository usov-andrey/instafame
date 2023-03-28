/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.field;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.vaadin.custom.component.LookupField;
import com.xalap.vaadin.custom.dialog.ChoosingListDialog;
import com.xalap.vaadin.custom.frame.RootEntityFrame;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @author Усов Андрей
 * @since 28/06/2019
 */
public class LookupFieldCreator<BEAN extends IdHolderWithName<ID>, ID, DIALOG extends ChoosingListDialog<GridBean, BEAN>,
        GridBean extends Serializable> implements FieldCreator<BEAN, BEAN, LookupField<BEAN, ID>> {

    private final Class<? extends RootEntityFrame<BEAN, ID>> entityFrameClass;
    private final Supplier<DIALOG> choosingDialogSupplier;

    /**
     * @param entityFrameClass - к какому экрану переходить при клике на выбранный элемент
     */
    public LookupFieldCreator(Class<? extends RootEntityFrame<BEAN, ID>> entityFrameClass, Supplier<DIALOG> choosingDialogSupplier) {
        this.entityFrameClass = entityFrameClass;
        this.choosingDialogSupplier = choosingDialogSupplier;
    }

    @Override
    public <Bean1> void bind(Binder.BindingBuilder<Bean1, BEAN> bindingBuilder, ValueProvider<Bean1, BEAN> valueProvider, Setter<Bean1, BEAN> setter) {
        bindingBuilder.bind(valueProvider, setter);
    }

    @Override
    public LookupField<BEAN, ID> createField(String caption) {
        return new LookupField<>(caption, entityFrameClass, choosingDialogSupplier.get());
    }
}
