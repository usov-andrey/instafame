/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 2020-05-04
 */
public class UrlRendererBuilder<T> extends RendererBuilder<T> {

    private UrlRendererBuilder(ValueProvider<T, ?> hrefProvider, ValueProvider<T, ?> textProvider) {
        super("<a href=\"{{item.url}}\" target=\"_blank\">{{item.urlText}}</a>");
        withProperty("url", hrefProvider);
        withProperty("urlText", textProvider);
    }

    public static <T> UrlRendererBuilder<T> url(ValueProvider<T, String> hrefProvider, ValueProvider<T, String> textProvider) {
        return new UrlRendererBuilder<>(hrefProvider, textProvider);
    }

    public static <T> UrlRendererBuilder<T> urlList(ValueProvider<T, List<String>> hrefProvider, ValueProvider<T,
            List<String>> textProvider) {
        return new UrlRendererBuilder<>(hrefProvider, textProvider);
    }
}
