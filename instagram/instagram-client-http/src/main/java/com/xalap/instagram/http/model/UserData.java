/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.api.user.User;

/**
 * @author Усов Андрей
 * @since 23.04.17
 */
public class UserData extends Data<User> {

    public UserData() {
    }

    public UserData(User object) {
        super(object);
    }
}
