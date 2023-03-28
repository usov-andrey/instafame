/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead.stage;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Repository
public interface LeadStageHistoryRepository extends PageableRepository<LeadStageHistoryBean, Integer> {

    List<LeadStageHistoryBean> findByLead(LeadBean lead);

    List<LeadStageHistoryBean> findByLeadAndStage(LeadBean lead, PipelineStageBean stage);
}
