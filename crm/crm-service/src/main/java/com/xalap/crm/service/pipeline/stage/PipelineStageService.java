/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage;

import com.xalap.crm.service.pipeline.PipelineService;
import com.xalap.framework.data.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Service
public class PipelineStageService extends CrudService<PipelineStageBean, PipelineStageRepository, Integer> {

    @Autowired
    private PipelineService pipelineService;

    public PipelineStageService() {
        super();
    }

    public PipelineStageBean getByCode(String code) {
        PipelineStageBean stageBean = repository().findByCode(code);
        if (stageBean == null) {
            stageBean = new PipelineStageBean();
            stageBean.setCode(code);
            stageBean.setName(code);
            stageBean.setPipeline(pipelineService.getDefaultPipeline());
            stageBean = save(stageBean);
        }
        return stageBean;
    }

}
