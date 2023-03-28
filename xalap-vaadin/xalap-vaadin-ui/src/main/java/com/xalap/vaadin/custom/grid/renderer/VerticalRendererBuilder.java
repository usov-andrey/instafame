/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;
import com.xalap.vaadin.custom.grid.renderer.core.RendererTemplateList;

import java.util.Arrays;
import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-05-06
 */
public class VerticalRendererBuilder<T> extends RendererBuilder<T> {

    private VerticalRendererBuilder(List<RendererBuilder<T>> items) {
        super("<vaadin-vertical-layout>%s</vaadin-vertical-layout>", new RendererTemplateList(items));
        for (RendererBuilder<T> item : items) {
            withRendererBuilders(item);
        }
    }

    @SafeVarargs
    public static <T> VerticalRendererBuilder<T> vertical(RendererBuilder<T>... items) {
        return vertical(Arrays.asList(items));
    }

    public static <T> VerticalRendererBuilder<T> vertical(List<RendererBuilder<T>> items) {
        return new VerticalRendererBuilder<>(items);
    }
}
