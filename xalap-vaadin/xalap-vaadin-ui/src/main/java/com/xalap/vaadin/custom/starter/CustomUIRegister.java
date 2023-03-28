/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.starter;

import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.custom.starter.appbar.CustomAppBar;
import com.xalap.vaadin.custom.starter.drawer.CustomNaviDrawer;
import com.xalap.vaadin.starter.custom.UICustomizer;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviDrawer;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

/**
 * Внедряем кастомную отрисовку MainLayout
 *
 * @author Usov Andrey
 * @since 2020-03-11
 */
@Service
public class CustomUIRegister {

    public CustomUIRegister(UICustomizer uiCustomizer, RouteChanger routeChanger) {
        uiCustomizer.setAppBarCreator(s -> new CustomAppBar(s, () -> uiCustomizer));
        naviDrawerCreator(uiCustomizer, () -> new CustomNaviDrawer(uiCustomizer, () -> routeChanger));
    }

    /**
     * Регистрируем свой NaviDrawerCreator
     */
    public void naviDrawerCreator(UICustomizer uiCustomizer, Supplier<NaviDrawer> naviDrawerCreator) {
        uiCustomizer.setNaviDrawerCreator(naviDrawerCreator);
    }

}
