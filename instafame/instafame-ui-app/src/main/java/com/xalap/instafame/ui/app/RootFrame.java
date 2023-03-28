/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.app;

import com.vaadin.flow.router.Route;
import com.xalap.vaadin.starter.ui.MainLayout;
import com.xalap.vaadin.starter.ui.views.Home;

/**
 * Экран открываемый по-умолчанию
 * @author Usov Andrey
 * @since 2020-03-11
 */
@Route(value = "", layout = MainLayout.class)
public class RootFrame extends Home {

}
