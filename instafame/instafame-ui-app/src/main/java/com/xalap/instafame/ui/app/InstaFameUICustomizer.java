/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.app;

import com.vaadin.flow.component.UI;
import com.xalap.crm.ui.CrmUI;
import com.xalap.framework.web.url.UrlManager;
import com.xalap.instafame.ui.InstaFameUI;
import com.xalap.instafame.ui.balance.ReplenishBalanceFrame;
import com.xalap.payment.ui.PaymentUI;
import com.xalap.uaa.ui.UaaUI;
import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.custom.UICustomizer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Настройка визуального вида vaadin приложения
 * @author Usov Andrey
 * @since 2020-03-13
 */
@Service
public class InstaFameUICustomizer {

    private final UICustomizer uiCustomizer;
    private final UrlManager urlManager;
    private final RouteChanger routeChanger;
    private final MenuCreator menuCreator;
    private final UaaUI uaaUI;
    private final CrmUI crmUI;
    private final InstaFameUI instaFameUI;
    private final PaymentUI paymentUI;

    public InstaFameUICustomizer(UICustomizer uiCustomizer, UrlManager urlManager, RouteChanger routeChanger,
                                 MenuCreator menuCreator, UaaUI uaaUI, CrmUI crmUI, InstaFameUI instaFameUI,
                                 PaymentUI paymentUI) {
        this.uiCustomizer = uiCustomizer;
        this.urlManager = urlManager;
        this.routeChanger = routeChanger;
        this.menuCreator = menuCreator;
        this.uaaUI = uaaUI;
        this.crmUI = crmUI;
        this.instaFameUI = instaFameUI;
        this.paymentUI = paymentUI;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        instaFameUI.addMenu(menuCreator);
        crmUI.addMenu();
        //paymentUI.addMenu();
        uaaUI.addMenu();
        //Баланс в action bar
        //paymentUI.addBalanceActionItem();

        //Настраиваем в init потому как какие-то другие модули могли сделать настройку по-умолчанию в конструкторе
        //А нам нужно, чтобы текущая настройка применилась последней
        uiCustomizer.setTitle("InstaFame");
        uiCustomizer.setLogoImgPath(urlManager.url("/images/logo.png"));
        uiCustomizer.setAvatarImageProvider(() -> urlManager.url("/images/no-avatar.png"));
        //Заменяем поиск на панель с балансом и кнопкой Оплатить
        //uiCustomizer.setNaviDrawerCreator(() -> new InstaFameNaviDrawer(uiCustomizer, routeChanger, paymentUI));
        //Переход к экрану Оплатить
        paymentUI.setPayButtonRunnable(() -> UI.getCurrent().navigate(ReplenishBalanceFrame.class));
    }
}
