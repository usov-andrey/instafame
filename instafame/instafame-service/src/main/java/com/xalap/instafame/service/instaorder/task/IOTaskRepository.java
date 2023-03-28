/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Repository
public interface IOTaskRepository extends PageableRepository<IOTaskBean, Integer> {

    String QUERY =
            "select task from IOTaskBean task " +
                    "inner join fetch task.order io " +
                    "left join fetch io.user ou " +
                    "left join fetch task.media m " +
                    "left join fetch io.order o " +
                    "left join fetch o.lead l " +
                    "left join fetch l.contact c " +
                    "left join fetch l.stage s " +
                    "left join fetch s.pipeline " +
                    "left join fetch task.cheatTask ct ";

    @Query(QUERY)
    @Override
    List<IOTaskBean> findAll();

    @Query(QUERY +
            "where ou.userName =:instagram and task.taskType =:taskType and task.status=:status")
    List<IOTaskBean> findByInstagramAndTaskTypeAndStatus(@Param("instagram") String instagram,
                                                         @Param("taskType") InstaOrderTaskType taskType,
                                                         @Param("status") InstaOrderTaskStatus status);

    @Query(QUERY +
            "where task.order =:order")
    List<IOTaskBean> findByOrder(@Param("order") IOBean order);

    List<IOTaskBean> findByStatus(InstaOrderTaskStatus status);

    @Query(QUERY +
            "where task.order in :orders")
    List<IOTaskBean> findByOrders(@Param("orders") Collection<IOBean> orderBeanList);

    @Query(QUERY +
            "where task.cheatTask =:cheatTask")
    List<IOTaskBean> findByCheatTask(@Param("cheatTask") CheatTaskBean cheatTask);
}
