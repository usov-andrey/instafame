/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.vaadin.flow.router.Route;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.IOTaskService;
import com.xalap.instafame.service.instaorder.task.history.IOTaskHistoryService;
import com.xalap.instafame.ui.instaorder.IOListFrame;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 21/06/2019
 */
@Route(value = IOTaskFrame.VIEW_NAME, layout = MainLayout.class)
public class IOTaskFrame extends RootEntityFrame<IOTaskBean, Integer> {

    public static final String VIEW_NAME = "instaOrderTask";

    @Autowired
    public IOTaskFrame(ServiceRef<IOTaskService> service, ServiceRef<IOTaskHistoryService> historyService) {
        super(service, IOListFrame.class);
        setViewContent(withTabs().addMainTab(service)
                .addTab("Статистика", new IOTaskHistoryListTab(historyService))
        );
    }

    @Override
    protected String getTitle() {
        return getBean().caption();
    }
}
