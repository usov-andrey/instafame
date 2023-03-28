/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.fluent;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class Image extends com.vaadin.flow.component.html.Image {

    public Image(String src) {
        setSrc(src);
    }

    public Image(String src, String alt) {
        this.setSrc(src);
        this.setAlt(alt);
    }

    public static Image image(String className, String text) {
        return new Image(text).className(className);
    }

    public Image className(String className) {
        setClassName(className);
        return this;
    }

    public Image alt(String className) {
        setAlt(className);
        return this;
    }

    public Image style(String value) {
        getElement().setAttribute("style", value);
        return this;
    }
}
