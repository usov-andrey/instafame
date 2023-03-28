/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import com.xalap.instagram.http.responsemodel.P;

import java.util.List;

/**
 * Задание на проверку правильного выбора предка
 * На знание и понимание this
 * На понимание инкапсуляции
 * @author Usov Andrey
 * @since 13.07.2021
 */
public class MenuTest extends BasePage {

    /**
     * Посещаем каждую страницу по одному разу
     * @param pageNames список страниц с возможными повторами
     */
    public void visitPages(List<String> pageNames) {
        PageFactory pageFactory = new PageFactory();
        for (String pageName : pageNames) {
            pageFactory.visit(pageFactory, pageName);
        }
    }
}
