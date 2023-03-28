/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattask;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = CheatTaskListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Сервисы поставщиков")
public class CheatTaskListFrame extends RootEntityListFrame<CheatTaskBean> {

    public static final String VIEW_NAME = "cheatTasks";

    @Autowired
    public CheatTaskListFrame(ServiceRef<CheatTaskService> service) {
        super(aClass -> new CheatTaskGridPanel(), service);

        addTab("Подписчики",
                cheatTaskBean -> cheatTaskBean.getTaskType() == InstaOrderTaskType.followers
                        && cheatTaskBean.activeAndEnabled()).
                addTab("Лайки",
                        cheatTaskBean -> cheatTaskBean.getTaskType() == InstaOrderTaskType.likes
                                && cheatTaskBean.activeAndEnabled()).
                addTab("Комментарии",
                        cheatTaskBean -> cheatTaskBean.getTaskType() == InstaOrderTaskType.comments
                                && cheatTaskBean.activeAndEnabled()).
                addTab("Просмотры",
                        cheatTaskBean -> cheatTaskBean.getTaskType() == InstaOrderTaskType.views
                                && cheatTaskBean.activeAndEnabled())
                .addTab("Все+неактивные");

        addCreateButton();
        gridPanel.buttons().addWithReload("Обновить", service.get()::updateTasks);

    }
}
