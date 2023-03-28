/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.html.Anchor;

/**
 * @author Усов Андрей
 * @since 30/04/2019
 */
public class InstagramComponent extends Anchor {

    public InstagramComponent(String userName) {
        super(accountUrl(userName), userName);
        setTarget("_blank");
    }

    public static String accountUrl(String userName) {
        return "https://www.instagram.com/" + userName;
    }

}
