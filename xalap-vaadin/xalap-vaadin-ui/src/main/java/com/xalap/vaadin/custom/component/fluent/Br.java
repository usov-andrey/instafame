/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;

/**
 * @author Usov Andrey
 * @since 2020-11-03
 */
@Tag("br")
public class Br extends HtmlComponent {

    public static Br br() {
        return new Br();
    }

}

