/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.scheduler;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.crm.service.scheduler.SchedulerTaskBean;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
@Route(value = SchedulerTaskListFrame.VIEW_NAME, layout = MainLayout.class)
public class SchedulerTaskListFrame extends RootEntityListFrame<SchedulerTaskBean> {

    public static final String VIEW_NAME = "schedTasks";

    @Autowired
    public SchedulerTaskListFrame(ServiceRef<SchedulerService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));

        GridColumns<SchedulerTaskBean> columns = gridPanel.columns();
        columns.addLink("Название", taskBean -> taskBean, SchedulerTaskFrame.class);
        columns.add(SchedulerTaskBean.PERIOD, SchedulerTaskBean.TIME,
                SchedulerTaskBean.LAST_EXEC_TIME);
        columns.addComponent("Статус", taskBean -> {
            Label label = new Label(taskBean.getStatus().name());
            if (!service.get().isRegisteredTask(taskBean)) {
                UIUtils.setTextColor(TextColor.ERROR, label);
            }
            return label;
        });

        columns.actions((actions, bean) ->
                actions.addWithReload("Запустить", () -> {
                    service.get().runTask(bean);
                }));
    }


}
