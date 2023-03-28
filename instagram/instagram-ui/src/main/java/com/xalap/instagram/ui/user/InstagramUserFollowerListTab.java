/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.ui.user;

import com.xalap.framework.utils.DateHelper;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.InstagramUserService;
import com.xalap.instagram.service.user.follower.UserFollowerBean;
import com.xalap.instagram.service.user.follower.UserFollowerService;
import com.xalap.vaadin.custom.component.InstagramComponent;
import com.xalap.vaadin.custom.component.IntegerField;
import com.xalap.vaadin.custom.grid.ComboFilterValue;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.tab.ListTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Информация об измении подписчиков инстаграм аккаунта
 *
 * @author Усов Андрей
 * @since 17/12/2019
 */
public class InstagramUserFollowerListTab extends ListTab<UserFollowerBean, InstagramUserBean> {

    public InstagramUserFollowerListTab(ServiceRef<InstagramUserService> userService,
                                        ServiceRef<UserFollowerService> service) {

        GridColumns<UserFollowerBean> columns = gridPanel.columns();
        columns.addComponent("Инстаграм", bean ->
                new InstagramComponent(bean.getFollowerUserName()));
        columns.add("Дата подписки", bean -> DateHelper.getDateTime(bean.getFollowDate())).add("Дата отписки", bean -> bean.getUnFollowDate() != null ? DateHelper.getDateTime(bean.getUnFollowDate()) : "");

        gridPanel.filters().addCombo("",
                Arrays.asList(
                        new ComboFilterValue<>("Подписчики", bean -> bean.getUnFollowDate() == null),
                        new ComboFilterValue<>("Отписавшиеся", bean -> bean.getUnFollowDate() != null),
                        new ComboFilterValue<>("Все", bean -> true)));

        IntegerField followersCount = new IntegerField();
        followersCount.setValue(500);
        gridPanel.buttons().add("Загрузить подписчиков:", () ->
                userService.get().readLastUserFollowers(getParentBean().getUserName(), followersCount.getValue())).add(followersCount);

        gridPanel.dataSource().setMemoryDataProvider(() -> {
            List<UserFollowerBean> byUser = service.get().repository().findByUser(getParentBean());
            byUser.sort(((Comparator<UserFollowerBean>) (o1, o2) -> {
                return o2.getFollowDate().compareTo(o1.getFollowDate());
            }).thenComparing(Comparator.comparing(UserFollowerBean::getId)));
            return byUser;
        });

    }

}
