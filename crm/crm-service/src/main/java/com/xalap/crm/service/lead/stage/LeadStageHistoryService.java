/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead.stage;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Service
public class LeadStageHistoryService extends CrudService<LeadStageHistoryBean, LeadStageHistoryRepository, Integer> {

    public LeadStageHistoryService() {
        super();
    }

    public void endStage(LeadBean leadBean) {
        Optional<LeadStageHistoryBean> current = current(leadBean);
        current.ifPresent(bean -> {
            bean.setEndTime(new Date());
            save(bean);
        });
    }

    private Optional<LeadStageHistoryBean> current(LeadBean leadBean) {
        List<LeadStageHistoryBean> historyBeanList = repository().findByLeadAndStage(leadBean, leadBean.getStage());
        return historyBeanList.stream().filter(bean -> bean.getEndTime() == null).findFirst();
    }

    public void startStage(LeadBean leadBean) {
        LeadStageHistoryBean bean = new LeadStageHistoryBean();
        bean.setStartTime(new Date());
        bean.setLead(leadBean);
        bean.setStage(leadBean.getStage());
        save(bean);
    }
}
