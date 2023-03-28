/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.app;

import com.xalap.payment.ui.PaymentUI;
import com.xalap.vaadin.custom.component.fluent.Div;
import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.custom.starter.drawer.CustomNaviDrawer;
import com.xalap.vaadin.starter.custom.UICustomizer;
import de.codecamp.vaadin.serviceref.ServiceRef;

/**
 * Заместо поиска по меню отображаем текущий баланс и кнопку Пополнить
 *
 * @author Usov Andrey
 * @since 2020-05-08
 */
public class InstaFameNaviDrawer extends CustomNaviDrawer {

    private Div balancePanel;

    InstaFameNaviDrawer(UICustomizer uiCustomizer, ServiceRef<RouteChanger> routeChanger, PaymentUI paymentUI) {
        super(uiCustomizer, routeChanger);
        balancePanel.add(paymentUI.createBalancePanel());
    }

    @Override
    protected void initSearch() {
        balancePanel = new Div();
        balancePanel.addClassNames("navi-item");
        mainContent.add(balancePanel);
    }
}
