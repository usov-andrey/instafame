/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Repository
public interface MediaRepository extends PageableRepository<MediaBean, Integer> {

    @EntityGraph(attributePaths = {"user"})
    List<MediaBean> findAll();

    @EntityGraph(attributePaths = {"user"})
    List<MediaBean> findByInternalId(String internalId);

    @EntityGraph(attributePaths = {"user"})
    List<MediaBean> findByCode(String code);

    @EntityGraph(attributePaths = {"user"})
    List<MediaBean> findByUser(InstagramUserBean user);

    @EntityGraph(attributePaths = {"user"})
    List<MediaBean> findByUserAndCreateTimeBetween(InstagramUserBean user, Date minCreateTime, Date maxCreateTime);
}
