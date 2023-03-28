/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.starter.ui.util.FontWeight;
import com.xalap.vaadin.starter.ui.util.UIUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 *
 * @author Usov Andrey
 * @since 2020-02-19
 */
public class ExtServiceComboBox extends ComboBox<CheatTaskBean> {

    public ExtServiceComboBox() {
        super();
        setClearButtonVisible(true);
        setItemLabelGenerator(CheatTaskBean::getName);
        setRenderer(new ComponentRenderer<>(item -> {

            VerticalLayout container = new CustomVerticalLayout();

            Label title = new Label(item.getProviderName() + "." + item.getServiceNumber() + " " + item.getName());
            container.add(title);
            UIUtils.setFontWeight(FontWeight.BOLDER, title);

            if (item.getComment() != null) {
                Label artist = new Label(item.getComment());
                artist.getStyle().set("fontSize", "smaller");
                container.add(artist);
            }

            container.add(new Label(item.getDescription()));
            container.add(new Label("Min/Max: " + item.getMin() + "/" + item.getMax()));

            Label priceLabel = new Label("Цена: " + StringHelper.toString(item.getCostFor1000()));
            UIUtils.setFontWeight(FontWeight.BOLDER, priceLabel);
            container.add(priceLabel);

            return container;
        }));
    }

    public ExtServiceComboBox(CheatTaskService cheatTaskService, InstaOrderTaskType taskType) {
        this(cheatTaskService.getEnabledSortedTasks(taskType));
    }

    public ExtServiceComboBox(List<CheatTaskBean> enabledTasks) {
        this();
        setItems(enabledTasks);
        setWidth("250px");
    }

    public void setDefaultValue(Supplier<Optional<CheatTaskBean>> valueSupplier) {
        Optional<CheatTaskBean> cheatTaskBean = valueSupplier.get();
        if (cheatTaskBean.isPresent() && cheatTaskBean.get().activeAndEnabled()) {
            setValue(cheatTaskBean.get());
        }
    }
}