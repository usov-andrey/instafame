/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.components.navigation.bar.AppBar;

/**
 * Экран с заголовком
 *
 * @author Usov Andrey
 * @since 2020-04-16
 */
public abstract class NavigationFrame extends ResizableFrame implements Navigation {

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        initAppBar(MainLayout.get().getAppBar());
    }

    protected void initAppBar(AppBar appBar) {
        String title = getTitle();
        appBar.setTitle(title);
        UI.getCurrent().getPage().setTitle(title);
    }

    abstract protected String getTitle();
}
