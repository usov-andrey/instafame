/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;


import com.xalap.instagram.api.user.User;

/**
 * @author Усов Андрей
 * @since 24.04.17
 */
public class Stats {

    private Integer media;
    private Integer followedBy;
    private Integer follows;
    private Integer likes;
    private Integer comments;

    public Stats() {
    }

    public Stats(User user) {
        media = user.getMediaCount();
        followedBy = user.getFollowedByCount();
        follows = user.getFollowsCount();
        likes = user.likeRatio();
        comments = user.commentRatio();
    }

    public Integer getMedia() {
        return media;
    }

    public void setMedia(Integer media) {
        this.media = media;
    }

    public Integer getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(Integer followedBy) {
        this.followedBy = followedBy;
    }

    public Integer getFollows() {
        return follows;
    }

    public void setFollows(Integer follows) {
        this.follows = follows;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "media=" + media +
                ", followedBy=" + followedBy +
                ", follows=" + follows +
                ", likes=" + likes +
                ", comments=" + comments;
    }
}
