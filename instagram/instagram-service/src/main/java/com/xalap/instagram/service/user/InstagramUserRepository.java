/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Repository
public interface InstagramUserRepository extends PageableRepository<InstagramUserBean, Integer> {

    InstagramUserBean findByInternalId(String internalId);

    InstagramUserBean findByUserName(String userName);

    List<InstagramUserBean> findByUpdateFreq(UpdateFreq updateFreq);
}
