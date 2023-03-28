/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.cheattask;

import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Route(value = CheatTaskListFrame.VIEW_NAME, layout = MainLayout.class)
public class CheatTaskFrame extends RootEntityFrame<CheatTaskBean, Integer> {

    @Autowired
    public CheatTaskFrame(ServiceRef<CheatTaskService> service, ServiceRef<IOTaskService> ioTaskService) {
        super(service, CheatTaskListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
                .addTab("Задачи", new CheatTaskIOTaskListTab(ioTaskService))
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
