/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user.follower;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 25/12/2018
 */
@Repository
public interface UserFollowerRepository extends PageableRepository<UserFollowerBean, Integer> {

    List<UserFollowerBean> findByUser(InstagramUserBean user);

}
