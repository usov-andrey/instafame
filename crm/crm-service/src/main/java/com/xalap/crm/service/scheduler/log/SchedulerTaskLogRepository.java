/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler.log;

import com.xalap.crm.service.scheduler.SchedulerTaskBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Repository
public interface SchedulerTaskLogRepository extends PageableRepository<SchedulerTaskLogBean, Integer> {

    List<SchedulerTaskLogBean> findByTask(SchedulerTaskBean task);
}
