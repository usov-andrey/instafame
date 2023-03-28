/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.task;

import com.xalap.instagram.service.api.InstagramClient;
import com.xalap.instagram.service.media.MediaBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Усов Андрей
 * @since 17/04/2019
 */
@Entity
public abstract class AbstractMediaTaskBean extends IOTaskBean {

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MediaBean media;
    private String code;

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public String getCode() {
        return code;
    }

    public AbstractMediaTaskBean setCode(String code) {
        this.code = code;
        return this;
    }

    public String url() {
        return InstagramClient.mediaUrl(getCode());
    }
}
