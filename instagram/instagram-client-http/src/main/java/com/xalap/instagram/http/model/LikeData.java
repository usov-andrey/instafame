/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.api.user.User;
import com.xalap.instagram.http.responsemodel.UserMedia;

/**
 * @author Усов Андрей
 * @since 23.04.17
 */
public class LikeData extends Data<MediaActivity> {

    public LikeData() {
    }

    public LikeData(MediaActivity object) {
        super(object);
    }

    public LikeData(UserMedia userMedia, User user) {
        this(new MediaActivity(userMedia.getId(), user.getUserName()));
    }
}
