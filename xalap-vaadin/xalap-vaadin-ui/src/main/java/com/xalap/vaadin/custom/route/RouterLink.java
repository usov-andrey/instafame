/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.route;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Router;
import com.xalap.framework.spring.BeanFactory;

import java.util.Map;

/**
 * @author Usov Andrey
 * @since 2020-05-07
 */
public class RouterLink extends com.vaadin.flow.router.RouterLink {

    public RouterLink() {

    }

    public RouterLink(String text, Class<? extends Component> navigationTarget) {
        super(text, navigationTarget);
    }

    public <T, C extends Component & HasUrlParameter<T>> RouterLink(String text, Class<? extends C> navigationTarget, T parameter) {
        super(text, navigationTarget, parameter);
    }

    public <T, C extends Component & HasUrlParameter<T>> RouterLink(Class<? extends C> navigationTarget, T parameter, Component... components) {
        super("", navigationTarget, parameter);
        add(components);
    }

    public <C extends Component> RouterLink(Class<? extends C> navigationTarget, Component... components) {
        super("", navigationTarget);
        add(components);
    }

    @Override
    public void setRoute(Router router, Class<? extends Component> navigationTarget) {
        super.setRoute(router, correct(navigationTarget));
    }

    @Override
    public <T, C extends Component & HasUrlParameter<T>> void setRoute(Router router, Class<? extends C> navigationTarget, T parameter) {
        super.setRoute(router, correct(navigationTarget), parameter);
    }

    @Override
    public void setRoute(Class<? extends Component> navigationTarget) {
        super.setRoute(correct(navigationTarget));
    }

    @Override
    public <T, C extends Component & HasUrlParameter<T>> void setRoute(Class<? extends C> navigationTarget, T parameter) {
        super.setRoute(correct(navigationTarget), parameter);
    }

    private <C> Class<C> correct(Class<? extends Component> navigationTarget) {
        return (Class<C>) BeanFactory.get(RouteChanger.class).route(navigationTarget);
    }

    public RouterLink queryParameters(Map<String, String> params) {
        setQueryParameters(QueryParameters.simple(params));
        return this;
    }

    public RouterLink queryParam(String name, String value) {
        return queryParameters(Map.of(name, value));
    }

    public RouterLink className(String className) {
        setClassName(className);
        return this;
    }

}
