/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer.core;

import com.xalap.framework.utils.StringHelper;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Usov Andrey
 * @since 2020-06-03
 */
public class RendererTemplateList implements Serializable {

    private final List<RendererTemplate> rendererTemplates;

    public <T> RendererTemplateList(List<RendererBuilder<T>> rendererBuilders) {
        rendererTemplates = rendererBuilders.stream().map(RendererBuilder::getTemplate).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return StringHelper.join(rendererTemplates, RendererTemplate::text, "");
    }
}
