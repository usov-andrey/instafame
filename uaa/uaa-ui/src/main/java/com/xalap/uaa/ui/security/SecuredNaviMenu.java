/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.ui.security;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.vaadin.custom.starter.drawer.CustomNaviMenu;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;

/**
 * Создаем пункт меню только если к нему есть доступ
 *
 * @author Usov Andrey
 * @since 05.05.2021
 */
public class SecuredNaviMenu extends CustomNaviMenu {

    private boolean noAccess(Class<? extends Component> navigationTarget) {
        return navigationTarget != null && !VaadinSecurityUtils.isAccessGranted(navigationTarget);
    }

    @Override
    public NaviItem addNaviItem(String text, Class<? extends Component> navigationTarget) {
        if (noAccess(navigationTarget)) {
            return null;
        }
        return super.addNaviItem(text, navigationTarget);
    }

    @Override
    public NaviItem addNaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        if (noAccess(navigationTarget)) {
            return null;
        }
        return super.addNaviItem(icon, text, navigationTarget);
    }

    @Override
    public NaviItem addNaviItem(Image image, String text, Class<? extends Component> navigationTarget) {
        if (noAccess(navigationTarget)) {
            return null;
        }
        return super.addNaviItem(image, text, navigationTarget);
    }

    @Override
    public NaviItem addNaviItem(NaviItem parent, String text, Class<? extends Component> navigationTarget) {
        if (noAccess(navigationTarget)) {
            return null;
        }
        return super.addNaviItem(parent, text, navigationTarget);
    }
}
