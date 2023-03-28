/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.RouterLink;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

import java.util.function.Function;

/**
 *
 * @author Usov Andrey
 * @since 2020-05-01
 */
public class RouterLinkRendererBuilder<T> extends RendererBuilder<T> {

    public <Id, X extends Component & HasUrlParameter<Id>> RouterLinkRendererBuilder(
            ValueProvider<T, String> captionProvider,
            Class<? extends X> viewClass, ValueProvider<T, Id> idProvider) {
        this(b -> new RouterLink(captionProvider.apply(b), viewClass, idProvider.apply(b)), captionProvider);
    }

    public RouterLinkRendererBuilder(Function<T, RouterLink> routerLinkFunction, Function<T, String> textFunction) {
        super("<a router-link href='{{item.l}}'>{{item.t}}</a>");
        withProperty("l", bean -> routerLinkFunction.apply(bean).getHref());
        withProperty("t", textFunction::apply);
    }

    public static <T, Id, X extends Component & HasUrlParameter<Id>> RendererBuilder<T> routerLink(
            ValueProvider<T, String> captionProvider,
            Class<? extends X> viewClass, ValueProvider<T, Id> idProvider) {
        return new RouterLinkRendererBuilder<>(captionProvider, viewClass, idProvider);
    }



}
