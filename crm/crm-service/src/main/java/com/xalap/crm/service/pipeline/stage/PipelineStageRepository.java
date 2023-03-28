/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage;

import com.xalap.crm.service.pipeline.PipelineBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Repository
public interface PipelineStageRepository extends PageableRepository<PipelineStageBean, Integer> {

    List<PipelineStageBean> findByPipeline(PipelineBean pipeline);

    PipelineStageBean findByCode(String code);
}
