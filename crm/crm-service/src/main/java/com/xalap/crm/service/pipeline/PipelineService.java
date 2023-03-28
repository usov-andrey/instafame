/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline;

import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Service
public class PipelineService extends CrudService<PipelineBean, PipelineRepository, Integer> {

    private static final String DEFAULT_CODE = "default";

    public PipelineService() {
        super();
    }


    public PipelineBean getDefaultPipeline() {
        PipelineBean bean = repository().findByCode(DEFAULT_CODE);
        if (bean == null) {
            bean = new PipelineBean();
            bean.setName(DEFAULT_CODE);
            bean.setCode(DEFAULT_CODE);
            bean = save(bean);
        }
        return bean;
    }

}
