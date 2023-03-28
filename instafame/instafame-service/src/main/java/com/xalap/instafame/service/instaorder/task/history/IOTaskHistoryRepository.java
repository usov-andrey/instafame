/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task.history;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Repository
public interface IOTaskHistoryRepository extends PageableRepository<IOTaskHistoryBean, Integer> {

    String QUERY =
            "select h from IOTaskHistoryBean h inner join fetch h.task task " +
                    "inner join fetch task.order io " +
                    "left join fetch io.user ou " +
                    "left join fetch task.media m " +
                    "left join fetch io.order o " +
                    "left join fetch o.lead l " +
                    "left join fetch l.contact c " +
                    "left join fetch l.stage s " +
                    "left join fetch s.pipeline " +
                    "left join fetch task.cheatTask ct ";

    @Query(QUERY +
            "where h.task = :task")
    List<IOTaskHistoryBean> findByTask(@Param("task") IOTaskBean task);

    @Query(QUERY +
            "where h.task in :tasks")
    List<IOTaskHistoryBean> findByTasks(@Param("tasks") Collection<IOTaskBean> tasks);
}
