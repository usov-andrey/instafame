/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Repository
public interface CheatTaskRepository extends PageableRepository<CheatTaskBean, Integer> {

    List<CheatTaskBean> findAll();

    List<CheatTaskBean> findByProviderName(String providerName);

    CheatTaskBean findByProviderNameAndServiceNumber(String name, int serviceNumber);

    List<CheatTaskBean> findByTaskTypeAndDisabled(InstaOrderTaskType taskType, boolean disabled);
}
