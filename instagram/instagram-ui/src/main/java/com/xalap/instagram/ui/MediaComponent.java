/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.xalap.instagram.service.media.MediaBean;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class MediaComponent extends Anchor {

    public MediaComponent(MediaBean mediaBean) {
        this(mediaBean, "200px");
    }

    public MediaComponent(MediaBean mediaBean, String size) {
        super(mediaBean.url());
        setTarget("_blank");
        Image image = new Image(mediaBean.getPreviewSrc(), "");
        image.setMaxHeight(size);
        image.setHeight(size);
        //image.setMaxWidth(size);
        add(image);
    }
}
