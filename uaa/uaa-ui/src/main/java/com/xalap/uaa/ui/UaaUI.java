/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.framework.web.url.UrlManager;
import com.xalap.uaa.ui.audit.AuditEventListFrame;
import com.xalap.uaa.ui.security.SecuredNaviMenu;
import com.xalap.uaa.ui.security.VaadinSecurityUtils;
import com.xalap.uaa.ui.user.UserListFrame;
import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.custom.starter.CustomUIRegister;
import com.xalap.vaadin.custom.starter.drawer.CustomNaviDrawer;
import com.xalap.vaadin.custom.starter.drawer.CustomNaviMenu;
import com.xalap.vaadin.starter.custom.LogoutMenuItem;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.custom.UICustomizer;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;
import org.springframework.stereotype.Component;

/**
 * Настройка пользовательского интерфейса
 *
 * @author Usov Andrey
 * @since 2020-04-10
 */
@Component
public class UaaUI {
    private final MenuCreator menuCreator;

    public UaaUI(MenuCreator menu, CustomUIRegister customUIRegister, UICustomizer uiCustomizer,
                 RouteChanger routeChanger, UrlManager urlManager) {
        this.menuCreator = menu;
        customUIRegister.naviDrawerCreator(uiCustomizer, () -> new CustomNaviDrawer(uiCustomizer, () -> routeChanger) {
            @Override
            protected CustomNaviMenu naviMenu() {
                return new SecuredNaviMenu();
            }
        });
        uiCustomizer.setMenuConsumer(naviMenu -> naviMenu.add(new LogoutMenuItem(urlManager.url("/logout")))
        );
    }

    public void addMenu() {
        menuCreator.add(menu -> {
            //Пока не сделан механизм, который будет не отображать родительские пункты меню, если список дочерних пустой
            //Используем эту проверку напрямую
            if (VaadinSecurityUtils.isAccessGranted(UserListFrame.class)) {
                NaviItem group = menu.addNaviItem(VaadinIcon.KEY, "Безопасность",
                        null);
                menu.addNaviItem(group,
                        "Учетные записи", UserListFrame.class);
                menu.addNaviItem(group,
                        "Аудит", AuditEventListFrame.class);
            }
        });
    }

}
