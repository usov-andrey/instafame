/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.function.SerializableRunnable;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOCommentsTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.vaadin.custom.component.TextWithDialog;
import com.xalap.vaadin.custom.grid.GridColumns;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 17/05/2019
 */
public class IOTaskGridColumnsBuilder extends GridColumns<IOTaskBean> {

    public IOTaskGridColumnsBuilder(Grid<IOTaskBean> grid, ServiceRef<IOTaskService> service,
                                    SerializableRunnable dataChangeHandler) {
        super(grid, IOTaskBean.class);
        addLink("Создано", taskBean -> DateHelper.getDateTime(taskBean.getCreateTime()),
                IOTaskBean::getId, IOTaskFrame.class).setWidth("100px");


        addComponent("Сервис", TaskLinkComponentBuilder::taskLink).setWidth("400px");
        addComponent("Количество:", taskBean -> {
            if (taskBean.isComments()) {
                IOCommentsTaskBean bean = taskBean.cast();
                if (StringHelper.isNotEmpty(bean.getComments())) {
                    return new TextWithDialog(bean.getComments());
                }
            }

            String count = taskBean.InProgressAndRemains() ?
                    new IOStats.Stat(taskBean.quantityMinusRemains(), taskBean.getQuantity()).caption() :
                    taskBean.quantityMinusRemains() + "";

            return new Label(count);
        }).setAutoWidth(true);
        add(IOTaskBean.START_COUNT, IOTaskBean.CHARGE, IOTaskBean.STATUS);
        actions(new IOTaskGridActions(service, dataChangeHandler));
    }
}
