/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.route;

import com.vaadin.flow.component.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Позволяет переопределять маршрут с одного класса на другой
 *
 * @author Usov Andrey
 * @since 2020-05-07
 */
@org.springframework.stereotype.Component
public class RouteChanger {

    private final Map<Class<? extends Component>, Class<? extends Component>> map = new HashMap<>();

    public RouteChanger reroute(Class<? extends Component> source, Class<? extends Component> target) {
        map.put(source, target);
        return this;
    }

    public Class<? extends Component> route(Class<? extends Component> navigationTarget) {
        return map.getOrDefault(navigationTarget, navigationTarget);
    }
}
