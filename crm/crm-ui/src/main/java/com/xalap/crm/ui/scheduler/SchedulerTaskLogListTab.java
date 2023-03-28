/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.scheduler;

import com.xalap.crm.service.scheduler.SchedulerTaskBean;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogBean;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogRepository;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.tab.Tab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Collections;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 10/05/2019
 */
public class SchedulerTaskLogListTab extends Tab<SchedulerTaskBean> {

    public SchedulerTaskLogListTab(ServiceRef<SchedulerTaskLogRepository> repository) {
        BeanWithIdGridPanel<SchedulerTaskLogBean, Integer> gridPanel = new BeanWithIdGridPanel<>(SchedulerTaskLogBean.class);
        gridPanel.dataSource().setMemoryDataProvider(() -> {
            List<SchedulerTaskLogBean> byTask = repository.get().findByTask(getParentBean());
            Collections.sort(byTask, (o1, o2) -> o2.getStartTime().compareTo(o1.getStartTime()));
            return byTask;
        });
        GridColumns<SchedulerTaskLogBean> columns = gridPanel.columns();
        columns.add(SchedulerTaskLogBean.START_TIME, SchedulerTaskLogBean.END_TIME, SchedulerTaskLogBean.STATUS);
        columns.add("Продолжительность", SchedulerTaskLogBean::duration);
        add(gridPanel);
    }

}
