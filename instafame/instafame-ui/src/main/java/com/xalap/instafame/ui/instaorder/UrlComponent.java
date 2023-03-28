/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.vaadin.flow.component.html.Anchor;

/**
 * @author Усов Андрей
 * @since 27/01/2020
 */
public class UrlComponent extends Anchor {

    public UrlComponent(String href) {
        this(href, href);
    }

    public UrlComponent(String href, String text) {
        super(href, text);
        setTarget("_blank");
    }
}
