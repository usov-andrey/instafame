/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.starter.drawer;

import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.starter.custom.UICustomizer;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.BrandExpression;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviDrawer;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviMenu;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Заменяем отрисовку логотипа и меню
 *
 * @author Usov Andrey
 * @since 2020-05-08
 */
public class CustomNaviDrawer extends NaviDrawer {

    private CustomBrandExpression brandExpression;
    private CustomNaviMenu naviMenu;

    public CustomNaviDrawer(UICustomizer uiCustomizer, ServiceRef<RouteChanger> routeChanger) {
        brandExpression.changeTitle(uiCustomizer.getTitle());
        brandExpression.changeLogoSrc(uiCustomizer.getLogoImgPath());
        naviMenu.setRouteChanger(routeChanger);
    }

    @Override
    protected BrandExpression getBrandExpression() {
        brandExpression = new CustomBrandExpression();
        return brandExpression;
    }

    @Override
    public NaviMenu createMenu() {
        naviMenu = naviMenu();
        return naviMenu;
    }

    protected CustomNaviMenu naviMenu() {
        return new CustomNaviMenu();
    }

}
