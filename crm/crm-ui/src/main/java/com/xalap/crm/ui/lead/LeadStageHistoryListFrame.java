/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.lead;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.crm.service.lead.stage.LeadStageHistoryBean;
import com.xalap.crm.service.lead.stage.LeadStageHistoryService;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;

/**
 * @author Усов Андрей
 * @since 02/10/2019
 */
@Route(value = LeadStageHistoryListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Переходы заявок")
public class LeadStageHistoryListFrame extends RootEntityListFrame<LeadStageHistoryBean> {

    public static final String VIEW_NAME = "leadStageHistoryList";

    @Autowired
    public LeadStageHistoryListFrame(ServiceRef<LeadStageHistoryService> service) {
        super(service, GridDefaultSorting.desc(COLUMN_ID));

        gridPanel.createColumns();

        addCreateButton();
    }
}
