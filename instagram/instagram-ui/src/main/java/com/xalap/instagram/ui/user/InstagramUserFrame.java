/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.ui.user;

import com.vaadin.flow.router.Route;
import com.xalap.framework.exception.RunnableWithServiceException;
import com.xalap.framework.exception.SupplierWithServiceException;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.instagram.service.user.follower.UserFollowerService;
import com.xalap.instagram.service.user.history.UserHistoryService;
import com.xalap.vaadin.custom.component.ButtonWithLog;
import com.xalap.vaadin.custom.component.PageReloadButton;
import com.xalap.vaadin.custom.component.layout.FlexHorizontalLayout;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Информация об инстаграм аккаунте
 *
 * @author Усов Андрей
 * @since 28/06/2019
 */
@Route(value = InstagramUserListFrame.VIEW_NAME, layout = MainLayout.class)
public class InstagramUserFrame extends RootEntityFrame<InstagramUserBean, Integer> {

    @Autowired
    public InstagramUserFrame(ServiceRef<InstagramUserService> service,
                              ServiceRef<UserHistoryService> userHistoryService,
                              ServiceRef<UserFollowerService> followerService) {
        super(service, InstagramUserListFrame.class);

        setViewContent(withTabs()
                .beforeTabs(
                        FlexHorizontalLayout.of(
                                new PageReloadButton("Обновить", event ->
                                        SupplierWithServiceException.run(
                                                () -> service.get().updateUserWithLastMedia(getBean().getUserName()))),
                                new ButtonWithLog("Загрузить все публикации", () -> {
                                    RunnableWithServiceException.run(() ->
                                            service.get().updateUserWithAllMedia(getBean().getUserName())
                                    );
                                }), new ButtonWithLog("Обновить подписчиков", () -> {
                                    service.get().updateUserFollowersHistory(getBean().getUserName());
                                })
                        )
                )
                .addMainTab(service)
                .addTab("Статистика", new InstagramUserHistoryListTab(userHistoryService))
                .addTab("Подписчики", new InstagramUserFollowerListTab(service, followerService))
        );
    }

    @Override
    protected String getTitle() {
        return getBean().getUserName();
    }
}
