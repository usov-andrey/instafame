/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.crm.ui.order.OrderFrame;
import com.xalap.crm.ui.order.OrderListFrame;
import com.xalap.crm.ui.quiz.QuizListFrame;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.cheat.CheatTaskService;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.settings.InstaOrderSettingsBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import com.xalap.instafame.ui.crm.InstaFameQuizListFrame;
import com.xalap.instafame.ui.crm.order.InstaFameOrderFrame;
import com.xalap.instafame.ui.crm.order.InstaFameOrderListFrame;
import com.xalap.instafame.ui.instaorder.IOListFrame;
import com.xalap.instafame.ui.instaorder.IOSettingsFrame;
import com.xalap.instafame.ui.instaorder.cheattask.CheatTaskChoosingListDialog;
import com.xalap.instafame.ui.instaorder.cheattask.CheatTaskFrame;
import com.xalap.instafame.ui.instaorder.cheattask.CheatTaskListFrame;
import com.xalap.instafame.ui.instaorder.cheattaskprovider.CheatTaskProviderListFrame;
import com.xalap.instafame.ui.instaorder.quality.IOTaskQualityListFrame;
import com.xalap.instafame.ui.instaorder.recovery.IORecoveryListFrame;
import com.xalap.instagram.ui.user.InstagramUserListFrame;
import com.xalap.vaadin.custom.field.FieldRegistry;
import com.xalap.vaadin.custom.field.LookupFieldCreator;
import com.xalap.vaadin.custom.route.RouteChanger;
import com.xalap.vaadin.starter.custom.MenuCreator;
import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviItem;
import org.springframework.stereotype.Service;

/**
 * Кастомизация и настройки UI
 *
 * @author Usov Andrey
 * @since 2020-02-19
 */
@Service
public class InstaFameUI {

    public InstaFameUI(FieldRegistry fieldRegistry, CheatTaskService cheatTaskService, RouteChanger routeChanger) {
        LookupFieldCreator<CheatTaskBean, Integer, CheatTaskChoosingListDialog, CheatTaskBean> cheatTaskLookupCreator =
                new LookupFieldCreator<>(CheatTaskFrame.class, () -> new CheatTaskChoosingListDialog(cheatTaskService, InstaOrderTaskType.followers));
        fieldRegistry.addForBean(cheatTaskLookupCreator,
                InstaOrderSettingsBean.class,
                InstaOrderSettingsBean.ECONOMY_FOLLOWERS, InstaOrderSettingsBean.PREMIUM_FOLLOWERS, InstaOrderSettingsBean.REAL_FOLLOWERS,
                InstaOrderSettingsBean.VIP_FOLLOWERS);
        fieldRegistry.addForBean(cheatTaskLookupCreator, IOBean.class, IOBean.FOLLOWERS_TASK);

        fieldRegistry.addForBean(new LookupFieldCreator<>(CheatTaskFrame.class, () -> new CheatTaskChoosingListDialog(cheatTaskService, InstaOrderTaskType.likes)),
                InstaOrderSettingsBean.class,
                InstaOrderSettingsBean.DEFAULT_LIKES);

        fieldRegistry.addForBean(new LookupFieldCreator<>(CheatTaskFrame.class, () -> new CheatTaskChoosingListDialog(cheatTaskService, InstaOrderTaskType.likes)),
                InstaOrderSettingsBean.class,
                InstaOrderSettingsBean.PREMIUM_LIKES);

        fieldRegistry.addForBean(new LookupFieldCreator<>(CheatTaskFrame.class, () -> new CheatTaskChoosingListDialog(cheatTaskService, InstaOrderTaskType.views)),
                InstaOrderSettingsBean.class,
                InstaOrderSettingsBean.DEFAULT_VIEWS);

        fieldRegistry.addForBean(new LookupFieldCreator<>(CheatTaskFrame.class, () -> new CheatTaskChoosingListDialog(cheatTaskService, InstaOrderTaskType.comments)),
                InstaOrderSettingsBean.class,
                InstaOrderSettingsBean.CUSTOM_COMMENTS);

        routeChanger
                .reroute(OrderListFrame.class, InstaFameOrderListFrame.class)
                .reroute(OrderFrame.class, InstaFameOrderFrame.class)
                .reroute(QuizListFrame.class, InstaFameQuizListFrame.class);
    }

    public void addMenu(MenuCreator menuCreator) {
        menuCreator.add(menu -> {
            //menu.addNaviItem(VaadinIcon.CART, "Мои заказы", MyIoListFrame.class);
            //menu.addNaviItem(VaadinIcon.CART, "Сделать заказ", ChoosingServiceFrame.class);
            NaviItem group = menu.addNaviItem(VaadinIcon.HEART, "Инстаграм",
                    null);
            menu.addNaviItem(group, "Обработка заказов", IOListFrame.class);
            menu.addNaviItem(group, "Эффективность гарантии", IORecoveryListFrame.class);
            menu.addNaviItem(group, "Задержки по заказам", IOTaskQualityListFrame.class);
            menu.addNaviItem(group, "Сервисы", CheatTaskListFrame.class);
            menu.addNaviItem(group, "Поставщики сервисов", CheatTaskProviderListFrame.class);
            menu.addNaviItem(group, "Пользователи", InstagramUserListFrame.class);
            menu.addNaviItem(group, "Настройки", IOSettingsFrame.class);
        });
    }

}
