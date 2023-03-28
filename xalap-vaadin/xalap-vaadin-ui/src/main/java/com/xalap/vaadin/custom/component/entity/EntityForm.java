/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.entity;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.function.SerializableFunction;
import com.xalap.framework.domain.entity.EntityWithIdService;
import com.xalap.framework.domain.holder.WithId;
import com.xalap.vaadin.custom.component.BeanForm;
import com.xalap.vaadin.custom.component.MenuButton;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFormFieldsCreator;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Форма + стандартные действия над сущностью
 * @author Усов Андрей
 * @since 15/06/2019
 */
public class EntityForm<B extends WithId> extends BeanForm<B> {
    private final SerializableFunction<B, List<Button>> buttonsCreator;//Какие кнопки отрисовывать
    private final EntityFormActions<B> actions = new EntityFormActions<>();
    private ServiceRef<? extends EntityWithIdService<B>> entityService;
    private SerializableBiConsumer<List<Button>, Optional<MenuButton>> buttonsDrawer;//Как отрисовывать кнопки

    public EntityForm(Class<B> beanClass, ServiceRef<? extends EntityWithIdService<B>> entityService) {
        this(new BinderFormFieldsCreator<>(beanClass), entityService);
    }

    public EntityForm(BinderFormFieldsCreator<B> binderFieldsCreator, ServiceRef<? extends EntityWithIdService<B>> entityService) {
        super(binderFieldsCreator);
        this.entityService = entityService;

        buttonsCreator = buttonsCreator();

        buttonsDrawer = (buttons, menuButtonOptional) -> {
            //По-умолчанию рисуем кнопки вверху формы справа
            HorizontalLayout layout = new HorizontalLayout();
            layout.setWidthFull();
            layout.setJustifyContentMode(JustifyContentMode.END);
            menuButtonOptional.ifPresent(layout::add);
            buttons.forEach(layout::add);
            addComponentAsFirst(layout);
        };
    }

    private SerializableFunction<B, List<Button>> buttonsCreator() {
        return bean -> {
            var bEntityService = entityService.get();
            //Отрисовываем кнопки
            //Сохранить
            List<Button> buttons = new ArrayList<>();
            Button saveButton = UIUtils.createPrimaryButton(getTranslation("button.save"));
            saveButton.addClickListener(buttonClickEvent -> {
                if (fieldsCreator().isValidationOk()) {
                    bEntityService.save(bean);
                    UIUtils.showNotification(getTranslation("notification.changes.saved"));
                }
            });
            buttons.add(saveButton);
            if (bEntityService.containsKey(bean)) {
                //Удалить
                Button deleteButton = UIUtils.createErrorButton(getTranslation("button.delete"));
                deleteButton.addClickListener(buttonClickEvent -> {
                    bEntityService.delete(bean);
                    UIUtils.showNotification(getTranslation("notification.object.deleted"));
                });
                buttons.add(deleteButton);
                //Сделать копию
                Button copyButton = UIUtils.createContrastButton(getTranslation("button.copy"));
                copyButton.addClickListener(buttonClickEvent -> {
                    bEntityService.copy(bean);
                    UIUtils.showNotification(getTranslation("notification.object.copy.created"));
                });
                buttons.add(deleteButton);
            }
            return buttons;
        };
    }

    @Override
    public void setBean(B bean) {
        super.setBean(bean);
        //Рисуем кнопки
        List<Button> buttons = buttonsCreator.apply(bean);
        Optional<MenuButton> actionsButtonOptional = actions.createButton(bean);
        buttonsDrawer.accept(buttons, actionsButtonOptional);
    }

    public void setButtonsDrawer(SerializableBiConsumer<List<Button>, Optional<MenuButton>> buttonsDrawer) {
        this.buttonsDrawer = buttonsDrawer;
    }

    protected EntityFormActions<B> formActions() {
        return actions;
    }

    public void setEntityService(ServiceRef<EntityWithIdService<B>> entityService) {
        this.entityService = entityService;
    }
}
