/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer.core;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.ValueProvider;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Usov Andrey
 * @since 2020-05-01
 */
public class RendererBuilder<T> implements Serializable {

    private final RendererTemplate template;
    private final Map<String, ValueProvider<T, ?>> valueProviderMap = new HashMap<>();
    private final Map<String, SerializableConsumer<T>> eventHandlers = new HashMap<>();
    private final Set<Class<? extends Component>> usedComponents = new HashSet<>();
    private final List<RendererBuilder<T>> children = new ArrayList<>();

    public RendererBuilder(String text, Serializable... params) {
        this.template = new RendererTemplate(text, params);
    }

    public static String createRandomSuffix() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        char[] chars = new char[5];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) random.nextInt('a', 'z' + 1);
        }

        return new String(chars);
    }

    @SafeVarargs
    protected final void withRendererBuilders(RendererBuilder<T>... rendererBuilders) {
        Collections.addAll(children, rendererBuilders);
    }

    public RendererBuilder<T> withProperty(String name, ValueProvider<T, ?> valueProvider) {
        valueProviderMap.put(name, valueProvider);
        return this;
    }

    public RendererBuilder<T> withEventHandler(String name, SerializableConsumer<T> eventHandler) {
        eventHandlers.put(name, eventHandler);
        return this;
    }

    public Set<Class<? extends Component>> getUsedComponents() {
        Set<Class<? extends Component>> result = new HashSet<>(usedComponents);
        for (RendererBuilder<T> child : children) {
            result.addAll(child.getUsedComponents());
        }
        return result;
    }

    public void withProperties(Map<String, ValueProvider<T, ?>> valueProviderMap) {
        this.valueProviderMap.putAll(valueProviderMap);
    }


    public RendererTemplate getTemplate() {
        return template;
    }

    public void replaceProperty(String oldPropertyName, String newPropertyName) {
        template.replace("{{item." + oldPropertyName + "}}", "{{item." + newPropertyName + "}}");
        ValueProvider<T, ?> valueProvider = valueProviderMap.remove(oldPropertyName);
        valueProviderMap.put(newPropertyName, valueProvider);
    }

    public void replaceEventHandlerName(String oldName, String newName) {
        eventHandlers.put(newName, eventHandlers.remove(oldName));
    }

    public Map<String, ValueProvider<T, ?>> getValueProviderMap() {
        return valueProviderMap;
    }

    public Map<String, SerializableConsumer<T>> getEventHandlers() {
        return eventHandlers;
    }

    void setupRender(CustomRenderer<T> renderer) {
        for (Map.Entry<String, ValueProvider<T, ?>> entry : getValueProviderMap().entrySet()) {
            renderer.setProperty(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, SerializableConsumer<T>> entry : getEventHandlers().entrySet()) {
            renderer.setEventHandler(entry.getKey(), entry.getValue());
        }

        for (RendererBuilder<T> child : children) {
            child.setupRender(renderer);
        }

    }

    public void makeUniqueKeys(UniqueKeyMaker<T> tUniqueKeyMaker) {
        tUniqueKeyMaker.makeUniqueKeys(this);
        tUniqueKeyMaker.makeUniqueKeys(children);
    }
}
