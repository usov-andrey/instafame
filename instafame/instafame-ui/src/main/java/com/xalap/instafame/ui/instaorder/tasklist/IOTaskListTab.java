/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOService;
import com.xalap.instafame.service.instaorder.IOStatus;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.vaadin.custom.component.MenuButton;
import com.xalap.vaadin.custom.grid.BeanWithIdGridPanel;
import com.xalap.vaadin.custom.tab.Tab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 19/04/2019
 */
public class IOTaskListTab extends Tab<IOBean> {

    public IOTaskListTab(ServiceRef<IOService> orderService, ServiceRef<IOTaskService> service, IOTaskListHolder taskListHolder) {

        BeanWithIdGridPanel<IOTaskBean, Integer> gridPanel = new BeanWithIdGridPanel<>(IOTaskBean.class);
        gridPanel.sortWithId();
        gridPanel.dataSource().setMemoryDataProvider(taskListHolder::getTaskBeanList);
        new IOTaskGridColumnsBuilder(gridPanel.grid(), service, taskListHolder::refresh);
        taskListHolder.addListener(value -> gridPanel.refreshAll());

        MenuButton menu = gridPanel.buttons().menu();
        menu
                .addMenu("Добавить задачу", () -> {
                    IOCreateTaskDialog createTaskView = new IOCreateTaskDialog(service);
                    createTaskView.setChangeHandler(taskListHolder::refresh);
                    createTaskView.setBean(new CreateTaskBean(getParentBean()));
                    createTaskView.openDialog();
                })
                .addMenu("Обновить прогресс", () -> taskListHolder.withRefresh(orderService.get()::updateOrder))
                .addMenu("Автозадания", () -> taskListHolder.withRefresh(service.get()::addAutoTasks))
                .addMenu("Восстановить", () -> taskListHolder.withRefresh(service.get()::restore))
                .addMenu("Запустить", () -> taskListHolder.withRefresh(service.get()::runTasks));

        add(gridPanel);

        //Когда появятся данные, дорисовываем
        parentBeanHolder.addListener(bean -> {

            if (bean.getStatus() == IOStatus.Confirmed || bean.getStatus() == IOStatus.Payed) {
                gridPanel.buttons().menu().addMenu("Начать",
                        taskListHolder.withRefresh(() -> orderService.get().startOrder(getParentBean())));
            }

        });
    }

}
