/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.ui.user;

import com.xalap.framework.utils.DateHelper;
import com.xalap.instagram.service.user.InstagramUserBean;
import com.xalap.instagram.service.user.history.UserHistoryBean;
import com.xalap.instagram.service.user.history.UserHistoryService;
import com.xalap.vaadin.custom.grid.GridColumns;
import com.xalap.vaadin.custom.tab.ListTab;
import de.codecamp.vaadin.serviceref.ServiceRef;

import java.util.List;

/**
 * История изменений показателей инстаграм аккаунта
 *
 * @author Усов Андрей
 * @since 22/04/2019
 */
public class InstagramUserHistoryListTab extends ListTab<UserHistoryBean, InstagramUserBean> {

    public InstagramUserHistoryListTab(ServiceRef<UserHistoryService> service) {

        GridColumns<UserHistoryBean> columns = gridPanel.columns();
        columns.add("Дата", userHistoryBean -> DateHelper.getDateTime(userHistoryBean.getCreateTime()))
                .add("Подписчиков",  userHistoryBean ->
                        Integer.toString(userHistoryBean.getUserStats().getFollowedByCount()))
                .add("Подписан", userHistoryBean ->
                        Integer.toString(userHistoryBean.getUserStats().getFollowsCount()))
                .add("Медиа", userHistoryBean ->
                        Integer.toString(userHistoryBean.getUserStats().getMediaCount()));

        gridPanel.dataSource().setMemoryDataProvider(() -> {
            List<UserHistoryBean> byUser = service.get().repository().findByUser(getParentBean());
            byUser.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
            return byUser;
        });
    }

}
