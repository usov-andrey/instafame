/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.custom;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;
import com.xalap.vaadin.starter.ui.views.Accounts;
import com.xalap.vaadin.starter.ui.views.Home;
import com.xalap.vaadin.starter.ui.views.Payments;
import com.xalap.vaadin.starter.ui.views.Statistics;
import com.xalap.vaadin.starter.ui.views.personnel.Accountants;
import com.xalap.vaadin.starter.ui.views.personnel.Managers;

/**
 * Создает меню для starter
 *
 * @author Usov Andrey
 * @since 2020-02-14
 */
public class StarterMenu {

    public StarterMenu(MenuCreator menuCreator) {
        menuCreator.add(menu -> {
            menu.addNaviItem(VaadinIcon.HOME, "Home", Home.class);

            menu.addNaviItem(VaadinIcon.INSTITUTION, "Accounts", Accounts.class);
            menu.addNaviItem(VaadinIcon.CREDIT_CARD, "Payments", Payments.class);
            menu.addNaviItem(VaadinIcon.CHART, "Statistics", Statistics.class);
            NaviItem personnel = menu.addNaviItem(VaadinIcon.USERS, "Personnel",
                    null);
            menu.addNaviItem(personnel, "Accountants", Accountants.class);
            menu.addNaviItem(personnel, "Managers", Managers.class);
        });
    }

}
