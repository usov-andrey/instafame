/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.instagram.ui.user.InstagramUserListFrame;
import com.xalap.vaadin.starter.custom.MenuCreator;

/**
 * @author Usov Andrey
 * @since 2020-02-14
 */
public class InstagramMenuCreator {

    public InstagramMenuCreator(MenuCreator menuProvider) {
        menuProvider.add(menu -> menu.addNaviItem(VaadinIcon.USERS,
                "Инста пользователи", InstagramUserListFrame.class));
    }
}
