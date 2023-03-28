/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.instagram.api.media.Media;

import javax.persistence.Embeddable;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Embeddable
public class MediaStatsBean {

    private int likesCount;
    private int commentsCount;
    private Integer viewVideoCount;

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Integer getViewVideoCount() {
        return viewVideoCount;
    }

    public void setViewVideoCount(Integer viewVideoCount) {
        this.viewVideoCount = viewVideoCount;
    }

    public int viewVideoCount() {
        return viewVideoCount == null ? 0 : viewVideoCount;
    }

    public void update(Media media) {
        likesCount = media.getLikesCount();
        commentsCount = media.getCommentsCount();
        viewVideoCount = media.getViewViewCount();
    }

    public boolean isChanged(Media media) {
        return likesCount != media.getLikesCount() || commentsCount != media.getCommentsCount()
                || (media.getViewViewCount() != null && !media.getViewViewCount().equals(viewVideoCount));
    }

    public int er() {
        return getCommentsCount() + getLikesCount();
    }

    @Override
    public String toString() {
        return "MediaStatsBean{" +
                "likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                '}';
    }
}
