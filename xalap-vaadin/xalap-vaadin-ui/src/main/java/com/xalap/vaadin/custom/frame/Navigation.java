/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.frame;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouteConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 21/06/2019
 */
public interface Navigation {

    default void back() {
        UI.getCurrent().getPage().getHistory().back();
    }

    default void forward() {
        UI.getCurrent().getPage().getHistory().forward();
    }

    default <T, C extends Component & HasUrlParameter<T>> void navigate(
            Class<? extends C> navigationTarget, T parameter) {
        UI.getCurrent().navigate(navigationTarget, parameter);
    }

    default void navigate(
            Class<? extends Component> navigationTarget) {
        UI.getCurrent().navigate(navigationTarget);
    }

    default void navigate(String url) {
        UI.getCurrent().navigate(url);
    }

    default <C extends Component> void navigate(
            Class<? extends C> navigationTarget, Map<String, String> parameters) {
        UI current = UI.getCurrent();
        RouteConfiguration configuration = RouteConfiguration
                .forRegistry(current.getRouter().getRegistry());
        current.navigate(configuration.getUrl(navigationTarget), QueryParameters.simple(parameters));
    }

    default <C extends Component> void redirect(
            Class<? extends C> navigationTarget, Map<String, String> parameters) {
        UI current = UI.getCurrent();
        current.getPage().setLocation(url(navigationTarget, parameters));
    }

    default <C extends Component> void redirect(
            Class<? extends C> navigationTarget) {
        UI current = UI.getCurrent();
        current.getPage().setLocation(url(navigationTarget, new HashMap<>()));
    }

    default <T, C extends Component & HasUrlParameter<T>> String url(
            Class<? extends C> navigationTarget, T parameter) {
        return configuration().getUrl(navigationTarget, parameter);
    }

    default String url(
            Class<? extends Component> navigationTarget) {
        return configuration().getUrl(navigationTarget);
    }

    default <C extends Component> String url(
            Class<? extends C> navigationTarget, Map<String, String> parameters) {
        Location location = new Location(configuration().getUrl(navigationTarget), QueryParameters.simple(parameters));
        return location.getPathWithQueryParameters();
    }

    default RouteConfiguration configuration() {
        UI current = UI.getCurrent();
        return RouteConfiguration
                .forRegistry(current.getRouter().getRegistry());
    }
}
