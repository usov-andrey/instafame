/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.function.SerializableConsumer;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.vaadin.custom.dialog.ChoosingListDialog;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;

import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class LookupField<B extends IdHolderWithName<Id>, Id> extends CustomField<B> {

    private final SerializableConsumer<B> valueSetter;
    private B value;
    private boolean navigationEnabled = true;

    /**
     * @param entityFrameClass - к какому экрану переходить при клике на выбранный элемент
     */
    public <D extends ChoosingListDialog<G, B>, G extends Serializable> LookupField(
            String caption, Class<? extends RootEntityFrame<B, Id>> entityFrameClass, D choosingDialog) {
        setLabel(caption);

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        buttons.setSpacing(true);
        buttons.setPadding(false);

        Icon chooseButton = icon(VaadinIcon.SEARCH);
        chooseButton.addClickListener(event -> choosingDialog.open(this::setValue));
        chooseButton.getStyle().set("margin-right", "5px");

        buttons.add(chooseButton);

        TextField field = new TextField();
        field.setSuffixComponent(buttons);
        field.setValue("Не задано");
        field.setWidthFull();

        valueSetter = b -> {
            value = b;
            if (value != null) {
                field.setValue(value.getName());
                buttons.removeAll();
                if (navigationEnabled) {
                    Icon goButton = icon(VaadinIcon.EYE);
                    goButton.addClickListener(event -> {
                        UI.getCurrent().navigate(entityFrameClass, value.getId());
                    });
                    buttons.add(goButton);
                }
                Icon clearButton = icon(VaadinIcon.CLOSE);
                clearButton.addClickListener(event -> {
                    setValue(null);
                });
                buttons.add(clearButton, chooseButton);

            } else {
                field.setValue("");
                buttons.removeAll();
                buttons.add(chooseButton);
            }
        };


        add(field);
    }

    public LookupField<B, Id> disableNavigation() {
        navigationEnabled = false;
        return this;
    }

    private Icon icon(VaadinIcon icon) {
        Icon i = new Icon(icon);
        i.setSize("14px");
        UIUtils.setTextColor(TextColor.SECONDARY, i);
        return i;
    }

    @Override
    protected B generateModelValue() {
        return value;
    }

    @Override
    protected void setPresentationValue(B b) {
        valueSetter.accept(b);
    }
}
