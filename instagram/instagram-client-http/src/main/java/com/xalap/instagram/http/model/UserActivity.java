/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;


/**
 * follow/unfollow над пользователем
 *
 * @author Усов Андрей
 * @since 22.04.17
 */
public class UserActivity {

    private String userName;

    public UserActivity() {
    }

    public UserActivity(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userName;
    }
}
