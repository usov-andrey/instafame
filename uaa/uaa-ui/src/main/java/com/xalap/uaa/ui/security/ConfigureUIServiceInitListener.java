/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.ui.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.xalap.framework.utils.WebHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Задаем необходимость авторизации при открытии экранов vaadin
 *
 * @author Usov Andrey
 * @since 2020-03-31
 */
@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    private final String loginPage;

    public ConfigureUIServiceInitListener(
            @Value("${security.login.page:login}") String loginPage) {
        this.loginPage = loginPage;
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::beforeEnter);
        });
    }

    /**
     * Reroutes the user if (s)he is not authorized to access the view.
     *
     * @param event before navigation event with event details
     */
    private void beforeEnter(BeforeEnterEvent event) {
        if (!VaadinSecurityUtils.isAccessGranted(event.getNavigationTarget())) {
            if (VaadinSecurityUtils.isUserLoggedIn()) {
                event.rerouteToError(NotFoundException.class);
            } else {
                event.rerouteTo(WebHelper.URL_SEPARATOR + loginPage);
            }
        }
    }
}