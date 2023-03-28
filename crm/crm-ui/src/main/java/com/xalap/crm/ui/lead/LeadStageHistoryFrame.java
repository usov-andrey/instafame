/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.lead;

import com.vaadin.flow.router.Route;
import com.xalap.crm.service.lead.stage.LeadStageHistoryBean;
import com.xalap.crm.service.lead.stage.LeadStageHistoryService;
import com.xalap.framework.utils.DateHelper;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 02/10/2019
 */
@Route(value = LeadStageHistoryListFrame.VIEW_NAME, layout = MainLayout.class)
public class LeadStageHistoryFrame extends RootEntityFrame<LeadStageHistoryBean, Integer> {

    @Autowired
    public LeadStageHistoryFrame(ServiceRef<LeadStageHistoryService> service) {
        super(service, LeadStageHistoryListFrame.class);
        setViewContent(withTabs()
                .addMainTab(service)
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getStage().getName() + " from " + DateHelper.getDateTime(getBean().getStartTime());
    }
}
