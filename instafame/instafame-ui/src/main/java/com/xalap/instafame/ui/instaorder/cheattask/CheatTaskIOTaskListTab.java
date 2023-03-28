/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattask;

import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskFrame;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.data.DataChangeListener;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.tab.ListTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 14/07/2019
 */
public class CheatTaskIOTaskListTab extends ListTab<IOTaskBean, CheatTaskBean> {

    public CheatTaskIOTaskListTab(ServiceRef<IOTaskService> ioTaskService) {
        gridPanel.dataSource().setMemoryDataProvider(() -> ioTaskService.get().repository().findByCheatTask(getParentBean())
                .stream().filter(taskBean -> taskBean.getSendTime() != null)
                .sorted((o1, o2) -> o2.getSendTime().compareTo(o1.getSendTime()))
                .limit(100).collect(Collectors.toList()));

        GridColumns<IOTaskBean> columns = gridPanel.columns();
        columns.addLink("Отправлено", taskBean -> DateHelper.getDateTime(taskBean.getSendTime()), IOTaskBean::getId,
                IOTaskFrame.class);
        columns.addComponent("Инстаграм", taskBean -> new InstagramComponent(taskBean.getOrder().getInstagram()));
        columns.add("Количество", taskBean -> "" + taskBean.quantityMinusRemains());
        columns.add("Время", IOTaskBean::duration);
        parentBeanHolder.addListener((DataChangeListener<CheatTaskBean>) value -> {
            if (value.getTaskType() == InstaOrderTaskType.followers) {
                columns.add("Подписчиков в сутки",  taskBean -> {
                    double perDay = ((long) taskBean.quantityMinusRemains()) * 1000 * 60 * 60 * 24 / taskBean.runServiceDuration();
                    return StringHelper.toString(perDay);
                });
            } else {
                columns.add("В час",  taskBean -> {
                    double perHour = taskBean.quantityMinusRemains() * 1000 * 60 * 60 / taskBean.runServiceDuration();
                    return StringHelper.toString(perHour);
                });
            }
        });


    }
}
