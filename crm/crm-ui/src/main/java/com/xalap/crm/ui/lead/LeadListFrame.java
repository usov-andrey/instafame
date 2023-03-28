/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.lead;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.order.OrderService;
import com.xalap.crm.service.quiz.QuizService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
@Route(value = LeadListFrame.VIEW_NAME, layout = MainLayout.class)
public class LeadListFrame extends RootEntityListFrame<LeadBean> {

    public static final String VIEW_NAME = "leadList";

    @Autowired
    public LeadListFrame(ServiceRef<LeadService> service, ServiceRef<QuizService> quizService, ServiceRef<OrderService> orderService) {
        super(aClass -> new LeadGridPanel(quizService, orderService), service);
        setDataProvider(service, desc(LeadBean.CREATE_TIME));
    }


}
