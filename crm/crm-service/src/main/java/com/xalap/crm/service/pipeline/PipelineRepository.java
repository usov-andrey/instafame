/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Repository
public interface PipelineRepository extends PageableRepository<PipelineBean, Integer> {


    PipelineBean findByCode(String code);
}
