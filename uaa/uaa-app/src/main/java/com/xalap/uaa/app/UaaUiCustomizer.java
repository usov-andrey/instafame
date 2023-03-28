/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.app;

import com.xalap.framework.web.url.UrlManager;
import com.xalap.uaa.ui.UaaUI;
import com.xalap.vaadin.starter.custom.UICustomizer;
import lombok.AllArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Настройка визуального вида vaadin приложения
 * @author Usov Andrey
 * @since 2020-03-13
 */
@Service
@AllArgsConstructor
public class UaaUiCustomizer {

    private final UICustomizer uiCustomizer;
    private final UrlManager urlManager;
    private final UaaUI uaaUI;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        uaaUI.addMenu();

        //Настраиваем в init потому как какие-то другие модули могли сделать настройку по-умолчанию в конструкторе
        //А нам нужно, чтобы текущая настройка применилась последней
        uiCustomizer.setTitle("UAA");
    }
}
