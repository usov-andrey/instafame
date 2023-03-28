/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

/*
 *
 */

package com.xalap.vaadin.starter.custom;

import com.xalap.vaadin.starter.ui.components.navigation.drawer.NaviMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 11/05/2019
 */
@Service
public class MenuCreator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final List<Consumer<NaviMenu>> menuCreators = new ArrayList<>();

    public void createMenu(NaviMenu menu, UICustomizer uiCustomizer) {
        try {
            for (Consumer<NaviMenu> menuCreator : menuCreators) {
                menuCreator.accept(menu);
            }
            uiCustomizer.addMenu(menu);
        } catch (Exception e) {
            //Vaadin проглатывает это исключение и выводит короткое сообщение, в котором непонятно где произошла ошибка
            log.error("Error on create menu:" + menu, e);
            throw new RuntimeException("Error on create menu " + menu, e);
        }
    }

    public MenuCreator add(Consumer<NaviMenu> menuConsumer) {
        menuCreators.add(menuConsumer);
        return this;
    }

}
