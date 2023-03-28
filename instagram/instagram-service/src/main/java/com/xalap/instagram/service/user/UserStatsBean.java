/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user;

import com.xalap.instagram.api.user.User;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Embeddable
public class UserStatsBean implements Serializable {

    private int followsCount;
    private int followedByCount;
    private int mediaCount;

    public void update(User user) {
        followsCount = user.getFollowsCount();
        followedByCount = user.getFollowedByCount();
        mediaCount = user.getMediaCount();
    }

    public String caption() {
        return mediaCount + " медиа " + followedByCount + " Подписчиков " + followsCount + " подписок";
    }

    public String stats() {
        return mediaCount + "/" + followedByCount + "/" + followsCount;
    }

    public boolean isChanged(User user) {
        return user.getMediaCount() != mediaCount ||
                user.getFollowsCount() != followsCount ||
                user.getFollowedByCount() != followedByCount;
    }

    public int getFollowsCount() {
        return followsCount;
    }

    public void setFollowsCount(int followsCount) {
        this.followsCount = followsCount;
    }

    public int getFollowedByCount() {
        return followedByCount;
    }

    public void setFollowedByCount(int followedByCount) {
        this.followedByCount = followedByCount;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(int mediaCount) {
        this.mediaCount = mediaCount;
    }

    @Override
    public String toString() {
        return "UserStatsBean{" +
                "followsCount=" + followsCount +
                ", followedByCount=" + followedByCount +
                ", mediaCount=" + mediaCount +
                '}';
    }
}
