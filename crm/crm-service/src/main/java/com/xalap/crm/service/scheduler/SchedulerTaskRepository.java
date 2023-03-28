/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Repository
public interface SchedulerTaskRepository extends PageableRepository<SchedulerTaskBean, Integer> {

    SchedulerTaskBean findByTaskClassName(String taskClassName);
}
