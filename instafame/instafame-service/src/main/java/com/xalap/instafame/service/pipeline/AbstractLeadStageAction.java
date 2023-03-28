/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.pipeline;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.lead.LeadService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public abstract class AbstractLeadStageAction implements Runnable {

    @Autowired
    private LeadService leadService;
    private Integer leadId;

    protected AbstractLeadStageAction() {
    }

    protected AbstractLeadStageAction(LeadBean leadBean) {
        leadId = leadBean.getId();
    }

    @Override
    public void run() {
        LeadBean leadBean = leadService.get(leadId);
        run(leadBean);
    }

    protected void save(LeadBean leadBean) {
        leadService.save(leadBean);
    }

    abstract void run(LeadBean leadBean);
}
