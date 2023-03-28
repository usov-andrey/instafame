/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 20/06/2019
 */
@Route(value = IOTaskForManualListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("ИнстаЗаказы с ошибками исполнения")
public class IOTaskForManualListFrame extends RootEntityListFrame<IOTaskBean> {

    public static final String VIEW_NAME = "ioTasksFormManualList";

    @Autowired
    public IOTaskForManualListFrame(ServiceRef<IOTaskService> service) {
        super(service);
        gridPanel.setColumnsBuilder(new IOTaskGridColumnsBuilder(gridPanel.grid(), service, this::reloadData));
        setMemoryDataProvider(() -> service.get().repository().findByStatus(InstaOrderTaskStatus.ManualCheck));
        gridPanel.buttons()
                .addWithReload("Перевести все в Created", () -> {
                    for (IOTaskBean ioTaskBean : gridPanel.dataSource().getItems()) {
                        ioTaskBean.setStatus(InstaOrderTaskStatus.Created);
                        service.get().save(ioTaskBean);
            }
                }).addWithReload("Удалить все", () -> gridPanel.dataSource().getItems().forEach(service.get()::delete));
    }

}
