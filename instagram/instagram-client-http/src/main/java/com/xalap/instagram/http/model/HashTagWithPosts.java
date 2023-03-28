/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;


import com.xalap.instagram.http.responsemodel.hashtag.Hashtag;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
public class HashTagWithPosts extends HashTag {

    private List<MediaWithLastActivity> topPosts;
    private List<MediaWithLastActivity> lastPosts;

    public HashTagWithPosts(Hashtag hashtag) {
        super(hashtag);
        topPosts = new ArrayList<>();
        lastPosts = new ArrayList<>();

    }

    public List<MediaWithLastActivity> getTopPosts() {
        return topPosts;
    }

    public void setTopPosts(List<MediaWithLastActivity> topPosts) {
        this.topPosts = topPosts;
    }

    public List<MediaWithLastActivity> getLastPosts() {
        return lastPosts;
    }

    public void setLastPosts(List<MediaWithLastActivity> lastPosts) {
        this.lastPosts = lastPosts;
    }

    public void addTop(MediaWithLastActivity mediaWithLastActivity) {
        topPosts.add(mediaWithLastActivity);
    }

    public void addLast(MediaWithLastActivity mediaWithLastActivity) {
        lastPosts.add(mediaWithLastActivity);
    }

    @Override
    public String toString() {
        return "HashTagWithPosts{" + super.toString() +
                ",topPosts=" + topPosts +
                ", lastPosts=" + lastPosts +
                '}';
    }
}
