/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.lead;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.stage.LeadStageHistoryBean;
import com.xalap.crm.service.lead.stage.LeadStageHistoryService;
import com.xalap.crm.ui.pipeline.stage.PipelineStageFrame;
import com.xalap.framework.utils.DateHelper;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.grid.valueprovider.DateTimeValueProvider;
import com.xalap.vaadin.custom.tab.ListDetailsTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * @author Усов Андрей
 * @since 02/10/2019
 */
public class LeadStageHistoryListTab extends ListDetailsTab<LeadStageHistoryBean, LeadBean> {

    public LeadStageHistoryListTab(LeadFrame frame, ServiceRef<LeadStageHistoryService> service) {
        super(frame, service);
        gridPanel.dataSource().setMemoryDataProvider(() -> service.get().repository().findByLead(getParentBean()));
        GridColumns<LeadStageHistoryBean> columns = gridPanel.columns();


        columns.addLink("Старт", leadStageHistoryBean ->
                DateHelper.getDateTime(leadStageHistoryBean.getStartTime()), LeadStageHistoryBean::getId,
                LeadStageHistoryFrame.class);
        columns.add("Окончание", new DateTimeValueProvider<>(LeadStageHistoryBean::getEndTime));
        columns.addLink("Этап",  leadStageHistoryBean -> leadStageHistoryBean.getStage().getName(),
                leadStageHistoryBean -> leadStageHistoryBean.getStage().getId(), PipelineStageFrame.class);
    }
}
