/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.xalap.framework.exception.SupplierWithServiceException;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.instagram.ui.user.InstagramUserFrame;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.starter.ui.util.TextColor;
import com.xalap.vaadin.starter.ui.util.UIUtils;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 07/05/2019
 */
public class UserInfoComponent extends FlexHorizontalLayout {

    public void setUser(String instagram, InstagramUserBean userBean, ServiceRef<InstagramUserService> userService,
                        Consumer<InstagramUserBean> userUpdatedConsumer) {
        removeAll();
        if (StringHelper.isNotEmpty(instagram)) {
            add(new InstagramComponent(instagram));
            if (userBean == null) {
                add(new ButtonWithLog("Обновить", () -> {
                    Optional<InstagramUserBean> userBeanOptional = SupplierWithServiceException.run(() ->
                            userService.get().updateUserWithLastMedia(instagram));
                    userBeanOptional.ifPresent(userUpdatedConsumer);
                }));
            }
        } else {
            add(new Label("Инстаграм не задан"));
            return;
        }
        if (userBean != null) {
            add(new Label("Медиа:" + userBean.getUserStats().getMediaCount() +
                    " Подписчиков: " +
                    userBean.getUserStats().getFollowedByCount() + "/" +
                    userBean.getUserStats().getFollowsCount()));
            if (userBean.isPrivate()) {
                Label label = new Label("Private");
                UIUtils.setTextColor(TextColor.ERROR, label);
                add(label);
            }
            add(new ButtonWithLog("Перейти", () -> {
                UI.getCurrent().navigate(InstagramUserFrame.class, userBean.getId());
            }));
            add(new ButtonWithLog("Обновить", () -> {
                InstagramUserBean userBean1 = updateUser(userService, userUpdatedConsumer, userBean);
                setUser(instagram, userBean1, userService, userUpdatedConsumer);
            }));
        } else {
            add(new Label("Пользователь не загружен или такого аккаунта не существует"));
        }
    }

    private InstagramUserBean updateUser(ServiceRef<InstagramUserService> userService, Consumer<InstagramUserBean> userUpdatedConsumer, InstagramUserBean userBean) {
        String userName = userBean.getUserName();
        Optional<InstagramUserBean> userBeanOptional = SupplierWithServiceException.run(() ->
                userService.get().updateUserWithLastMedia(userName));
        if (userBeanOptional.isPresent()) {
            userBean = userBeanOptional.get();
            userUpdatedConsumer.accept(userBean);
        }
        return userBean;
    }
}
