/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.starter.custom;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;

/**
 * Пункт меню выхода
 *
 * @author Usov Andrey
 * @since 20.05.2021
 */
public class LogoutMenuItem extends Div {

    public LogoutMenuItem(String logoutUrl) {
        setClassName("navi-item");
        Div divLink = new Div();
        add(divLink);
        divLink.setClassName("navi-item__link");
        Span span = new Span("Выйти");
        Anchor logout = new Anchor(logoutUrl, span);
        divLink.add(VaadinIcon.EXIT.create(), logout);
    }
}
