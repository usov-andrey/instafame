/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattask;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.RouterLink;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;

import java.util.Comparator;

/**
 * @author Усов Андрей
 * @since 10/07/2019
 */
public class CheatTaskGridPanel extends BeanWithIdGridPanel<CheatTaskBean, Integer> {

    public CheatTaskGridPanel() {
        super(CheatTaskBean.class);
        dataSource().setSortComparator((o1, o2) -> Comparator.comparing(CheatTaskBean::getTaskType)
                .thenComparing(CheatTaskBean::getProviderName)
                .thenComparing(CheatTaskBean::getCostFor1000).compare(o1, o2));


        GridColumns<CheatTaskBean> columns = columns();
        columns.addComponent("No", cheatTaskBean -> {
            Label label = new Label("" + cheatTaskBean.getServiceNumber());
            TextColor color;
            if (cheatTaskBean.isDisabled()) color = TextColor.ERROR;
            else color = cheatTaskBean.isActive() ? TextColor.SUCCESS : TextColor.DISABLED;
            UIUtils.setTextColor(color, label);
            return label;
        }).setWidth("50px");
        columns.addComponent("Название", cheatTaskBean -> {
            CustomVerticalLayout layout = new CustomVerticalLayout();
            layout.add(new RouterLink(cheatTaskBean.getName(), CheatTaskFrame.class, cheatTaskBean.getId()));
            if (cheatTaskBean.getDescription() != null) {
                layout.add(cheatTaskBean.getDescription());
            }
            if (StringHelper.isNotEmpty(cheatTaskBean.getComment())) {
                layout.add(cheatTaskBean.getComment());
            }
            return layout;
        }).setFlexGrow(5);
        columns.addColumn(CheatTaskBean.PROVIDER).setWidth("50px");
        columns.add(CheatTaskBean.MIN, CheatTaskBean.MAX, CheatTaskBean.PRICE);
        columns.add(CheatTaskBean.RATING, CheatTaskBean.RUSSIAN, CheatTaskBean.CANCELABLE);
    }
}
