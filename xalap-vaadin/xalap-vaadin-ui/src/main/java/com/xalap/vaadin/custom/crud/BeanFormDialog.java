/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.crud;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.xalap.vaadin.custom.crud.binderfieldscreator.BinderFormFieldsCreator;
import com.xalap.vaadin.custom.handler.ChangeHandler;

import java.io.Serializable;

/**
 * Форма создания обьекта
 * Отображается или в модальном окне или в списке справа
 *
 * @author Усов Андрей
 * @since 18/04/2019
 */
public abstract class BeanFormDialog<B extends Serializable> extends VerticalLayout implements KeyNotifier, AfterNavigationObserver {

    private final FormLayout formLayout = new FormLayout();
    protected B bean;
    protected Binder<B> binder = new Binder<>();
    private BinderFormFieldsCreator<B> binderFieldsCreator;
    private boolean draw = true;
    private Dialog dialog;
    private ChangeHandler changeHandler;

    protected BeanFormDialog(Class<B> beanClass) {
        binderFieldsCreator = new BinderFormFieldsCreator<>(beanClass);
    }

    public void setBinderFieldsCreator(BinderFormFieldsCreator<B> binderFieldsCreator) {
        this.binderFieldsCreator = binderFieldsCreator;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        if (draw) {
            draw = false;
            draw();
        }
    }

    private void draw() {
        //Формируем поля
        addFields();

        //Рисуем форму с кнопками
        addFormWithActions();
        // Configure and style components
        setSpacing(true);
    }

    protected void addFields() {
        binderFieldsCreator.setBinder(binder);
        binderFieldsCreator.setLayout(formLayout);
        binderFieldsCreator.addFields();
    }

    protected <FIELDVALUE, F extends Component & HasValue<?, FIELDVALUE>> Binder.BindingBuilder<B, FIELDVALUE> addField(F field) {
        formLayout.add(field);
        return binder.forField(field);
    }

    protected <FIELDVALUE, F extends Component & HasValue<?, FIELDVALUE>> Binder.BindingBuilder<B, FIELDVALUE> addRequiredField(F field) {
        return addField(field).asRequired();
    }

    /**
     * Рисуем форму с кнопками
     */
    protected void addFormWithActions() {
        add(createFormWithActions());
    }

    protected VerticalLayout createFormWithActions() {
        return dialog != null ?
                new VerticalLayout(createForm(), createActions()) :
                new VerticalLayout(createActions(), createForm());
    }

    protected FormLayout createForm() {
        setupFormLayout(formLayout);
        return formLayout;
    }

    protected void setupFormLayout(FormLayout formLayout) {
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("21em", 2),
                new FormLayout.ResponsiveStep("21em", 3),
                new FormLayout.ResponsiveStep("21em", 4));
    }

    /**
     * Действия над формой
     */
    protected HorizontalLayout createActions() {
        return new HorizontalLayout(createSaveButton(), createCancelButton());
    }

    protected Button createCancelButton() {
        Button cancel = new Button("Cancel");
        cancel.addClickListener(e -> {
            onCancel();
        });
        return cancel;
    }

    protected void onCancel() {
        if (dialog != null) {
            dialog.close();
        } else {
            setVisible(false);
        }
    }

    protected Button createSaveButton() {
        Button saveButton = new Button("Save", VaadinIcon.CHECK.create());
        saveButton.getElement().getThemeList().add("primary");
        saveButton.addClickListener(e -> onSave());
        return saveButton;
    }

    private void onSave() {
        save(bean);
        onChange();
        if (dialog != null) {
            dialog.close();
        }
    }

    abstract protected void save(B bean);

    protected void onChange() {
        if (changeHandler != null) {
            changeHandler.onChange();
        }
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public void setBean(B bean) {
        this.bean = bean;
        binder.setBean(bean);
    }

    /**
     * Открываем диалог и рисуем экран
     */
    public void openDialog() {
        dialog = new Dialog();
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);
        dialog.add(this);
        draw();
        dialog.open();
    }

}