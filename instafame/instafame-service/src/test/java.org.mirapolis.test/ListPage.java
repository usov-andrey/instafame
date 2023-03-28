/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

import java.util.ArrayList;

/**
 *
 * @author Usov Andrey
 * @since 13.07.2021
 */
public class ListPage extends BasePage {

    private String name;

    public ListPage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void visit(PageFactory pageFactory) {
        super.visit(pageFactory);
    }
}
