/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

/**
 * Like/Unlike
 *
 * @author Усов Андрей
 * @since 23.04.17
 */
public class MediaActivity {

    private String mediaId;
    private String userName;

    public MediaActivity() {
    }

    public MediaActivity(String mediaId, String userName) {
        this.mediaId = mediaId;
        this.userName = userName;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
