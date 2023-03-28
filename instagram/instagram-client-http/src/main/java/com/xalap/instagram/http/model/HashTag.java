/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.http.responsemodel.hashtag.Hashtag;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 23.05.2018
 */
public class HashTag {

    private String name;
    private String profilePicUrl;
    private Date readTime;//время чтения информации о этом хэштеге
    private int mediaCount;

    public HashTag(Hashtag hashtag) {
        name = hashtag.getName();
        profilePicUrl = hashtag.getProfilePicUrl();
        readTime = new Date();
        mediaCount = hashtag.getEdgeHashtagToMedia().getCount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(int mediaCount) {
        this.mediaCount = mediaCount;
    }

    @Override
    public String toString() {
        return "HashTag{" +
                "name='" + name + '\'' +
                ", profilePicUrl='" + profilePicUrl + '\'' +
                ", readTime=" + readTime +
                ", mediaCount=" + mediaCount +
                '}';
    }
}
