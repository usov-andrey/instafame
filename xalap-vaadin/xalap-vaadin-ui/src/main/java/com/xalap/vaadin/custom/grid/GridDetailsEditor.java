/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.WithId;
import com.xalap.vaadin.custom.component.DetailsWindow;
import com.xalap.vaadin.custom.component.detailsdrawer.EmptyDetailsDrawerFooter;
import com.xalap.vaadin.custom.component.entity.EntityForm;
import com.xalap.vaadin.custom.component.entity.EntityWithUpdateService;
import com.xalap.vaadin.starter.ui.util.LumoStyles;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Окно быстрого редактирования строчки в гриде
 *
 * @author Усов Андрей
 * @since 12/06/2019
 */
public class GridDetailsEditor<B extends WithId, GRID_BEAN extends Serializable> extends DetailsWindow<GRID_BEAN> {

    private final ServiceRef<EntityWithIdService<B>> entityWithUpdateService;
    private EntityForm<B> form;

    public GridDetailsEditor(GridPanel<GRID_BEAN> gridPanel, Class<B> beanClass, Function<GRID_BEAN, B> beanFunction,
                             ServiceRef<EntityWithIdService<B>> entityService) {
        gridPanel.setSelectionListener(gridBean -> showContent(beanFunction.apply(gridBean)));
        this.entityWithUpdateService = createEntityWithUpdateService(entityService);
        this.form = new EntityForm<>(beanClass, entityWithUpdateService);
        addBeanListener(value -> setContent(beanFunction.apply(value)));
    }

    private ServiceRef<EntityWithIdService<B>> createEntityWithUpdateService(ServiceRef<EntityWithIdService<B>> entityService) {
        return () -> new EntityWithUpdateService<>(entityService, b -> detailsDrawer.hide());
    }

    /**
     * Этот мето должен вызываться только один раз после создания обьекта
     */
    public void drawForm() {
        form.addClassNames(LumoStyles.Padding.Bottom.L, LumoStyles.Padding.Horizontal.L, LumoStyles.Padding.Top.S);
        form.createFields();
        //Добавляем кнопки
        form.setButtonsDrawer((buttons, menuButtonOptional) -> {
            // Footer
            detailsDrawerFooter = new EmptyDetailsDrawerFooter();
            detailsDrawer.setFooter(detailsDrawerFooter);

            for (Button button : buttons) {
                detailsDrawerFooter.add(button);
            }

            menuButtonOptional.ifPresent(detailsDrawerFooter::add);

        });
    }

    private void setContent(B bean) {
        detailsDrawerHeader.setTitle(!entityWithUpdateService.get().containsKey(bean) ?
                getTranslation("adding") : getTranslation("editing"));
        detailsDrawer.setContent(form);
        form.setBean(bean);
    }

    public EntityForm<B> getForm() {
        return form;
    }

    public void setForm(EntityForm<B> form) {
        this.form = form;
        form.setEntityService(entityWithUpdateService);
    }

    public Button createAddButton() {
        return createAddButton(() -> entityWithUpdateService.get().createNew());
    }

    public Button createAddButton(Supplier<B> supplier) {
        Button addNewBtn = new Button(getTranslation("button.add"), VaadinIcon.PLUS.create());
        addNewBtn.addClickListener(e -> showContent(supplier.get()));
        return addNewBtn;
    }

    protected void showContent(B bean) {
        setContent(bean);
        showDrawer();
    }

    protected String getTranslation(String key) {
        return form.getTranslation(key);
    }

}
