/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.html.ListItem;

import java.util.Arrays;
import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-10-06
 */
public class Ul extends com.vaadin.flow.component.html.UnorderedList {

    public static Ul ul(String className) {
        return new Ul().className(className);
    }

    public Ul className(String className) {
        setClassName(className);
        return this;
    }

    public Ul addLi(Li li) {
        add(li);
        return this;
    }

    public Ul addLi(String... values) {
        return addLi(Arrays.asList(values));
    }

    public Ul addLi(List<String> values) {
        for (String value : values) {
            ListItem li = new ListItem(value);
            add(li);
        }
        return this;
    }

}
