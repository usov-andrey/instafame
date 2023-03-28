/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer.core;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.renderer.LabelRendererBuilder;
import com.xalap.vaadin.custom.grid.renderer.TextRendererBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Usov Andrey
 * @since 2020-05-01
 */
public class RendererBuilders<T> implements Serializable {

    private final UniqueKeyMaker<T> propertyUniqueKeyMaker;
    private final UniqueKeyMaker<T> eventHandlerNameUniqueKeyMaker;

    private final List<RendererBuilder<T>> rendererBuilderList = new ArrayList<>();

    public RendererBuilders(UniqueKeyMaker<T> propertyUniqueKeyMaker, UniqueKeyMaker<T> eventHandlerNameUniqueKeyMaker) {
        this.propertyUniqueKeyMaker = propertyUniqueKeyMaker;
        this.eventHandlerNameUniqueKeyMaker = eventHandlerNameUniqueKeyMaker;
    }

    public static <T> RendererBuilders<T> create(RendererBuilders<T> builders) {
        return new RendererBuilders<>(builders.propertyUniqueKeyMaker, builders.eventHandlerNameUniqueKeyMaker);
    }

    public static <T> RendererBuilders<T> create() {
        return new RendererBuilders<>(
                new UniqueKeyMaker<>("p",
                        rendererBuilder -> rendererBuilder.getValueProviderMap().keySet(),
                        RendererBuilder::replaceProperty),
                new UniqueKeyMaker<>("e",
                        rendererBuilder -> rendererBuilder.getEventHandlers().keySet(),
                        RendererBuilder::replaceEventHandlerName));
    }

    public RendererBuilders<T> createNew() {
        return new RendererBuilders<>(propertyUniqueKeyMaker, eventHandlerNameUniqueKeyMaker);
    }

    public RendererBuilders<T> add(RendererBuilder<T> rendererBuilder) {
        rendererBuilderList.add(rendererBuilder);
        return this;
    }

    public RendererBuilders<T> add(String label, ValueProvider<T, String> valueProvider) {
        return add(LabelRendererBuilder.label(label, TextRendererBuilder.text(valueProvider)));
    }

    public RendererBuilders<T> add(String label, RendererBuilder<T> item) {
        return add(LabelRendererBuilder.label(label, item));
    }

    public Renderer<T> build() {
        //Вначале возможно нужно изменить имена параметров
        propertyUniqueKeyMaker.makeUniqueKeys(rendererBuilderList);
        eventHandlerNameUniqueKeyMaker.makeUniqueKeys(rendererBuilderList);

        CustomRenderer<T> renderer = new CustomRenderer<>(renderTemplate(), getUsedComponents());
        for (RendererBuilder<T> rendererBuilder : rendererBuilderList) {
            rendererBuilder.setupRender(renderer);
        }

        return renderer;
    }

    private Set<Class<? extends Component>> getUsedComponents() {
        Set<Class<? extends Component>> usedComponents = new HashSet<>();
        for (RendererBuilder<T> rendererItem : rendererBuilderList) {
            usedComponents.addAll(rendererItem.getUsedComponents());
        }
        return usedComponents;
    }

    private String renderTemplate() {
        StringBuilder template = new StringBuilder();
        for (RendererBuilder<T> rendererItem : rendererBuilderList) {
            template.append(rendererItem.getTemplate().text());
        }
        return template.toString();
    }

}
