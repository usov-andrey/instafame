/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.quality;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOFollowersPackage;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryBean;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryProvider;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryService;
import com.xalap.instafame.service.instaorder.task.provider.MemoryIOTaskProvider;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskFrame;
import com.xalap.instafame.ui.instaorder.tasklist.IOTaskGridActions;
import com.xalap.instafame.ui.instaorder.tasklist.TaskLinkComponentBuilder;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.ComboFilterValue;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 25/06/2019
 */
@Route(value = IOTaskQualityListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Качество заказов")
public class IOTaskQualityListFrame extends RootEntityListFrame<IOTaskBean> {

    public static final String VIEW_NAME = "ioTaskQualityList";

    @Autowired
    public IOTaskQualityListFrame(ServiceRef<IOTaskService> service, ServiceRef<IOService> instaOrderService,
                                  ServiceRef<IOTaskHistoryService> historyService) {
        super(service);
        IOTaskHistoryProvider historyProvider = new IOTaskHistoryProvider();
        setMemoryDataProvider(() -> {
            List<IOBean> notCompleted = instaOrderService.get().getNotCompleted();
            MemoryIOTaskProvider memoryIOTaskProvider = service.get().memoryTaskProvider(notCompleted);
            List<IOTaskBean> tasks = memoryIOTaskProvider.getTasks(InstaOrderTaskStatus.Completed, InstaOrderTaskStatus.InProgress);
            historyProvider.setValue(historyService.get(), tasks);
            return tasks;
        });

        //-----Вкладки
        addTab("Лайки", taskBean -> taskBean.getTaskType() == InstaOrderTaskType.likes).addTab("Подписчики Эконом", taskBean -> taskBean.getTaskType() == InstaOrderTaskType.followers
                && taskBean.getOrder().getFollowersPackage() == IOFollowersPackage.economy).addTab("Подписчики Премиум", taskBean -> taskBean.getTaskType() == InstaOrderTaskType.followers
                && (taskBean.getOrder().getFollowersPackage() == IOFollowersPackage.premium ||
                taskBean.getOrder().getFollowersPackage() == IOFollowersPackage.live ||
                taskBean.getOrder().getFollowersPackage() == IOFollowersPackage.vip)).addTab("Комментарии", taskBean -> taskBean.getTaskType() == InstaOrderTaskType.comments);
        //-----Filters
        gridPanel.filters().addCombo("",
                Arrays.asList(
                        new ComboFilterValue<>("In Progress", taskBean -> taskBean.getStatus() == InstaOrderTaskStatus.InProgress),
                        new ComboFilterValue<>("All", taskBean -> true)));
        gridPanel.filters()
                .addDate("C", (orderBean, date) -> orderBean.getCreateTime().after(date))
                .addDate("По", (orderBean, date) -> orderBean.getCreateTime().before(date));

        gridPanel.dataSource().setSortComparator((o1, o2) -> Long.compare(o1.runServiceDuration(), o2.runServiceDuration()));

        GridColumns<IOTaskBean> columns = gridPanel.columns();
        columns.addLink("Отправлено", taskBean -> DateHelper.getDateTime(taskBean.sendTime()), IOTaskBean::getId,
                IOTaskFrame.class);
        columns.add("Первый прогресс", (taskBean) -> {
            Optional<IOTaskHistoryBean> firstHistoryTime = historyProvider.firstHistory(taskBean);
            return firstHistoryTime.map(ioTaskHistoryBean -> DateHelper.formatDuration(
                    DateHelper.duration(taskBean.getSendTime(), ioTaskHistoryBean.getCreateTime()))).orElse("");
        });
        columns.addComponent("Последний прогресс", taskBean -> {
            Date date = historyProvider.lastHistoryTime(taskBean);
            Label label = new Label(DateHelper.formatDuration(DateHelper.duration(date, new Date())));
            if (taskBean.isNotifyOnDelay() &&
                    DateHelper.hoursBetweenDates(date, new Date()) >= instaOrderService.get().getMaxDelayHours()) {
                UIUtils.setTextColor(TextColor.ERROR, label);
            }
            return label;
        });
        columns.add("Время", IOTaskBean::duration);
        columns.addComponent("Инстаграм", taskBean -> new InstagramComponent(taskBean.getOrder().getInstagram()));
        columns.add("Прогресс", taskBean -> new IOStats.Stat(taskBean.quantityMinusRemains(), taskBean.getQuantity()).caption());

        columns.addComponent("Сервис", TaskLinkComponentBuilder::taskLink);

        columns.add(IOTaskBean.COUNT_SPEED);
        columns.add("Статус", taskBean -> taskBean.getStatus().toString() +
                (taskBean.getCheatTask() != null && taskBean.getCheatTask().isDisabled() ? "(Disabled)" :
                        taskBean.getCheatTask() != null && !taskBean.getCheatTask().isActive() ? "(NotActive)" :
                                ""));
        columns.actions(new IOTaskGridActions(service, this::reloadData));
    }

}
