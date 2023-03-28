/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.starter.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviMenu;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Добавляем возможность изменения Route
 *
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class CustomNaviMenu extends NaviMenu {

    private ServiceRef<RouteChanger> routeChanger;

    public void setRouteChanger(ServiceRef<RouteChanger> routeChanger) {
        this.routeChanger = routeChanger;
    }

    @Override
    public NaviItem addNaviItem(String text, Class<? extends Component> navigationTarget) {
        return super.addNaviItem(text, correct(navigationTarget));
    }

    @Override
    public NaviItem addNaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        return super.addNaviItem(icon, text, correct(navigationTarget));
    }

    @Override
    public NaviItem addNaviItem(Image image, String text, Class<? extends Component> navigationTarget) {
        return super.addNaviItem(image, text, correct(navigationTarget));
    }

    @Override
    public NaviItem addNaviItem(NaviItem parent, String text, Class<? extends Component> navigationTarget) {
        return super.addNaviItem(parent, text, correct(navigationTarget));
    }

    private Class<? extends Component> correct(Class<? extends Component> navigationTarget) {
        return routeChanger.get().route(navigationTarget);
    }
}
