/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer.core;

import com.xalap.framework.utils.StringHelper;

import java.io.Serializable;

/**
 * @author Usov Andrey
 * @since 2020-06-03
 */
public class RendererTemplate implements Serializable {

    private final Serializable[] params;
    private String text;

    public RendererTemplate(String text, Serializable... params) {
        this.text = text;
        this.params = params;
    }

    public String text() {
        return String.format(text, params);
    }

    public void replace(String from, String to) {
        text = StringHelper.replace(text, from, to);
    }

    @Override
    public String toString() {
        return text();
    }
}
