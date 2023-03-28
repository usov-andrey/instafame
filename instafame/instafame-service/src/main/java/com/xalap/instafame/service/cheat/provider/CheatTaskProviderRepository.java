/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Repository
public interface CheatTaskProviderRepository extends PageableRepository<CheatTaskProviderBean, Integer> {

    List<CheatTaskProviderBean> findAll();

    CheatTaskProviderBean findByName(String name);
}
