/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.api.user.User;

import java.util.HashSet;

/**
 * @author Усов Андрей
 * @since 08.05.17
 */
public class UserListData extends Data<HashSet<User>> {

    public UserListData() {
    }

    public UserListData(HashSet<User> object) {
        super(object);
    }
}
