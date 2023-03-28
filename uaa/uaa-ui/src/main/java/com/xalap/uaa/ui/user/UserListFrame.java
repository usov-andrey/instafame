/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.ui.user;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.xalap.uaa.api.user.UserApi;
import com.xalap.uaa.api.user.UserDto;
import com.xalap.vaadin.custom.frame.RootEntityListFrame;
import com.xalap.vaadin.custom.grid.dataprovider.filter.GridSearchSqlDataProvider;
import com.xalap.vaadin.starter.ui.MainLayout;
import de.codecamp.vaadin.serviceref.ServiceRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import static com.xalap.framework.domain.holder.IdHolder.COLUMN_ID;
import static com.xalap.uaa.api.Role.ROLE_ADMIN;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.asc;
import static com.xalap.vaadin.custom.grid.dataprovider.GridDefaultSorting.desc;

/**
 * Список пользователей
 *
 * @author Usov Andrey
 * @since 2020-04-10
 */
@Route(value = UserListFrame.VIEW_NAME, layout = MainLayout.class)
@PageTitle("Пользователи")
@Secured(ROLE_ADMIN)
public class UserListFrame extends RootEntityListFrame<UserDto> {

    static final String VIEW_NAME = "users";

    @Autowired
    public UserListFrame(ServiceRef<UserApi> service) {
        super(service, asc(COLUMN_ID));

        setDataProviderWithSearch(
                new GridSearchSqlDataProvider<>(
                        service,
                        desc(COLUMN_ID)));
        var columnBuilder = gridPanel.columnBuilder();
        columnBuilder.add("Логин", UserDto::getLogin);
        columnBuilder.add("Имя", UserDto::getName);
        columnBuilder.add("Email", UserDto::getEmail);
        columnBuilder.addBoolean("Активен", UserDto::getActivated);
        addCreateButton();
    }

}
