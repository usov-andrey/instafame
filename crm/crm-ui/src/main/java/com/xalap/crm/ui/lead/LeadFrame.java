/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.lead;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import com.xalap.crm.service.lead.stage.LeadStageHistoryService;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
@Route(value = LeadListFrame.VIEW_NAME, layout = MainLayout.class)
public class LeadFrame extends RootEntityFrame<LeadBean, Integer> {

    @Autowired
    public LeadFrame(ServiceRef<LeadService> service, ServiceRef<LeadStageHistoryService> historyService) {
        super(service, LeadListFrame.class);
        setViewContent(withTabs().addMainTab(service)
                .addTab("История", new LeadStageHistoryListTab(this, historyService))
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getName();
    }
}
