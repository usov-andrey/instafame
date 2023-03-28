/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

/**
 * @author Usov Andrey
 * @since 2020-05-04
 */
public class TextRendererBuilder<T> extends RendererBuilder<T> {

    private TextRendererBuilder(ValueProvider<T, ?> valueProvider) {
        this("t" + createRandomSuffix(), valueProvider);
    }

    private TextRendererBuilder(String propertyName, ValueProvider<T, ?> valueProvider) {
        super("{{item." + propertyName + "}}");
        withProperty(propertyName, valueProvider);
    }

    public static <T> TextRendererBuilder<T> text(ValueProvider<T, ?> valueProvider) {
        return new TextRendererBuilder<>(valueProvider);
    }
}
