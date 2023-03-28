/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.pipeline.stage;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.crm.ui.lead.LeadGridPanel;
import com.xalap.vaadin.custom.tab.ListTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 14/09/2019
 */
public class PipelineStageLeadListTab extends ListTab<LeadBean, PipelineStageBean> {

    public PipelineStageLeadListTab(ServiceRef<LeadService> leadService, ServiceRef<QuizService> quizService,
                                    ServiceRef<OrderService> orderService) {
        super(aClass -> new LeadGridPanel(quizService, orderService));
        gridPanel.dataSource().setMemoryDataProvider(() -> leadService.get().repository().findByStage(getParentBean()));
    }
}
