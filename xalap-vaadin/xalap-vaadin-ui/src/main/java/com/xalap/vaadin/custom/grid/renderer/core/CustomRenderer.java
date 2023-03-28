/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer.core;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.internal.UIInternals;
import com.vaadin.flow.data.provider.DataKeyMapper;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.data.renderer.Rendering;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.ValueProvider;

import java.util.Set;

/**
 * @author Usov Andrey
 * @since 2020-05-04
 */
public class CustomRenderer<T> extends Renderer<T> {

    private final Set<Class<? extends Component>> usedComponents;

    public CustomRenderer(String template, Set<Class<? extends Component>> usedComponents) {
        super(template);
        this.usedComponents = usedComponents;
    }

    @Override
    public Rendering<T> render(Element container, DataKeyMapper<T> keyMapper) {
        if (!usedComponents.isEmpty()) {
            container.getNode().runWhenAttached(ui -> {
                UIInternals internals = ui.getInternals();
                usedComponents.forEach(internals::addComponentDependencies);
            });
        }
        return super.render(container, keyMapper);
    }

    // Expose protected methods to the outer class
    @Override
    protected void setEventHandler(String handlerName, SerializableConsumer<T> handler) {
        super.setEventHandler(handlerName, handler);
    }

    @Override
    protected void setProperty(String property, ValueProvider<T, ?> provider) {
        super.setProperty(property, provider);
    }
}
