/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user.follower;

import com.xalap.framework.data.CrudService;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 17/12/2019
 */
@Service
public class UserFollowerService extends CrudService<UserFollowerBean, UserFollowerRepository, Integer> {

    public UserFollowerService() {
        super();
    }

    public UserFollowerBean createAndSave(InstagramUserBean userBean, String follower) {
        //Новый пользователь
        UserFollowerBean userFollowerBean = new UserFollowerBean();
        userFollowerBean.setUser(userBean);
        userFollowerBean.setFollowerUserName(follower);
        userFollowerBean.setFollowDate(new Date());
        return repository().save(userFollowerBean);
    }
}
