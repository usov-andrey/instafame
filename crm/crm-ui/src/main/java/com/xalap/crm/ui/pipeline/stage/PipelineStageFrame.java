/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.pipeline.stage;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Route(value = PipelineStageListFrame.VIEW_NAME, layout = MainLayout.class)
public class PipelineStageFrame extends RootEntityFrame<PipelineStageBean, Integer> {

    @Autowired
    public PipelineStageFrame(ServiceRef<PipelineStageService> service, ServiceRef<LeadService> leadService,
                              ServiceRef<QuizService> quizService, ServiceRef<OrderService> orderService) {
        super(service, PipelineStageListFrame.class);
        setViewContent(withTabs()
                        .addMainTab(service)
                        .addTab("Заявки", new PipelineStageLeadListTab(leadService, quizService, orderService))
                //.addTab("Действия", new PipelineStageActionListTab(this, actionService))
                //.addTab("Задачи", new PipelineStatusListTab(this, statusService)
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
