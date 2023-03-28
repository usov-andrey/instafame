/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer.core;

import com.vaadin.flow.function.SerializableFunction;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Usov Andrey
 * @since 2020-05-06
 */
public class UniqueKeyMaker<T> implements Serializable {

    private final Set<String> keyStore = new HashSet<>();
    private final String prefix;
    private final SerializableFunction<RendererBuilder<T>, Set<String>> keySetFunction;
    private final KeyReplacer<T> keyReplacer;

    public UniqueKeyMaker(String prefix, SerializableFunction<RendererBuilder<T>, Set<String>> keySetFunction, KeyReplacer<T> keyReplacer) {
        this.prefix = prefix;
        this.keySetFunction = keySetFunction;
        this.keyReplacer = keyReplacer;
    }

    public void makeUniqueKeys(Collection<RendererBuilder<T>> rendererBuilders) {
        for (RendererBuilder<T> rendererItem : rendererBuilders) {
            rendererItem.makeUniqueKeys(this);
        }
    }

    public void makeUniqueKeys(RendererBuilder<T> rendererItem) {
        Set<String> keySet = new HashSet<>(keySetFunction.apply(rendererItem));
        for (String key : keySet) {
            while (!keyStore.add(key)) {
                String newKey = prefix + RendererBuilder.createRandomSuffix();
                keyReplacer.replace(rendererItem, key, newKey);
                key = newKey;
            }
        }
    }


    @FunctionalInterface
    public interface KeyReplacer<T> extends Serializable{
        void replace(RendererBuilder<T> t, String oldKey, String newKey);
    }
}
