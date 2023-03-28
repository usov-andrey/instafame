/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.scheduler;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.crm.service.scheduler.SchedulerTaskBean;
import com.xalap.crm.service.scheduler.log.SchedulerTaskLogRepository;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
@Route(value = SchedulerTaskListFrame.VIEW_NAME, layout = MainLayout.class)
public class SchedulerTaskFrame extends RootEntityFrame<SchedulerTaskBean, Integer> {

    @Autowired
    public SchedulerTaskFrame(ServiceRef<SchedulerService> service, ServiceRef<SchedulerTaskLogRepository> logRepository) {
        super(service, SchedulerTaskListFrame.class);
        setViewContent(withTabs().addMainTab(service)
                .addTab("Журнал", new SchedulerTaskLogListTab(logRepository)));
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
