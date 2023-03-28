/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage.action;

import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Repository
public interface PipelineStageActionRepository extends PageableRepository<PipelineStageActionBean, Integer> {

    List<PipelineStageActionBean> findByStage(PipelineStageBean stage);

}
