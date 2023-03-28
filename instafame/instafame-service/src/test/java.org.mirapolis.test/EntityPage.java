/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.parser;

/**
 * @author Usov Andrey
 * @since 13.07.2021
 */
public class EntityPage extends ListPage {

    public EntityPage(String name) {
        super(name);
    }

    @Override
    public void visit(PageFactory pageFactory) {
        System.out.println("Page:" + getName());
    }
}
