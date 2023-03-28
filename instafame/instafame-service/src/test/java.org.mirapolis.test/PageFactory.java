/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Usov Andrey
 * @since 13.07.2021
 */
public class PageFactory {

    /**
     * Сет с уникальными типами страниц, чтобы не было повторных проверок на страницах
     */
    public Set<String> uniqueTypes = new HashSet<>();

    public void visit(PageFactory pageFactory, String pageName) {
        BasePage page = getPage(pageName);
        page.visit(pageFactory);
    }

    public BasePage getPage(String pageName) {
        if (pageName.contains("List")) {
            return new ListPage(pageName);
        } else if (pageName.contains("Entity")) {
            return new EntityPage(pageName);
        }
        return null;
    }
}
