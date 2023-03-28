/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.instagram.service.user.InstagramUserBean;

import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 04.07.2018
 */
public class MediaListener {

    private final InstagramUserBean userBean;
    private final Consumer<MediaBean> newMediaConsumer;
    private final Runnable mediaUpdatedListener;

    public MediaListener(InstagramUserBean userBean, Consumer<MediaBean> newMediaConsumer, Runnable mediaUpdatedListener) {
        this.userBean = userBean;
        this.newMediaConsumer = newMediaConsumer;
        this.mediaUpdatedListener = mediaUpdatedListener;
    }

    public void newComments(MediaBean mediaBean) {

    }

    public void newLikes(MediaBean mediaBean) {

    }

    public void newMedia(MediaBean mediaBean) {
        newMediaConsumer.accept(mediaBean);
    }

    public void setUser(MediaBean mediaBean) {
        if (userBean != null) {
            mediaBean.setUser(userBean);
        }
    }

    public InstagramUserBean getUserBean() {
        return userBean;
    }

    public void mediaUpdated() {
        mediaUpdatedListener.run();
    }

}
