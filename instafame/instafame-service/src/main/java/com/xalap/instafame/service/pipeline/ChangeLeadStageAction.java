/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.pipeline;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.crm.service.pipeline.stage.PipelineStage;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class ChangeLeadStageAction extends AbstractLeadStageAction {


    @Autowired
    private PipelineStageService stageService;


    private Integer oldStageId;
    private String newStageCode;

    public ChangeLeadStageAction() {
    }

    public ChangeLeadStageAction(LeadBean leadBean, PipelineStage newStage) {
        super(leadBean);
        oldStageId = leadBean.getStage().getId();
        newStageCode = newStage.getCode();
    }

    @Override
    protected void run(LeadBean leadBean) {
        if (leadBean != null && leadBean.getStage() != null && leadBean.getStage().getId().equals(oldStageId)) {
            PipelineStageBean stageBean = stageService.getByCode(newStageCode);
            if (stageBean != null) {
                leadBean.setStage(stageBean);
                save(leadBean);
            }
        }
    }

}
