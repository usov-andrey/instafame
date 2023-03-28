/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

/**
 * @author Usov Andrey
 * @since 2020-05-01
 */
public class LabelRendererBuilder<T> extends RendererBuilder<T> {

    private LabelRendererBuilder(RendererBuilder<T> label, RendererBuilder<T> item) {
        super("<div><span>%s</span><span>&nbsp;</span><span>%s</span></div>", label.getTemplate(), item.getTemplate());
        withRendererBuilders(label, item);
    }

    private LabelRendererBuilder(String template, RendererBuilder<T> item) {
        super(template, item.getTemplate());
        withRendererBuilders(item);
    }

    public static <T> LabelRendererBuilder<T> label(String label, ValueProvider<T, String> valueProvider) {
        return label(label, TextRendererBuilder.text(valueProvider));
    }

    public static <T> LabelRendererBuilder<T> label(String label, RendererBuilder<T> item) {
        return new LabelRendererBuilder<>("<div>" + label + ": <span>%s</span></div>", item);
    }

    public static <T> LabelRendererBuilder<T> label(RendererBuilder<T> label, ValueProvider<T, String> valueProvider) {
        return label(label, TextRendererBuilder.text(valueProvider));
    }

    public static <T> LabelRendererBuilder<T> label(RendererBuilder<T> label, RendererBuilder<T> item) {
        return new LabelRendererBuilder<>(label, item);
    }

    public static <T> LabelRendererBuilder<T> labelWithFloat(String label, ValueProvider<T, String> valueProvider) {
        return labelWithFloat(label, TextRendererBuilder.text(valueProvider));
    }

    public static <T> LabelRendererBuilder<T> labelWithFloat(String label, RendererBuilder<T> item) {
        /*
        return new LabelRendererBuilder<>(s -> "<div>" + label + ": <span>" + s + "</span></div>", item);*/
        return new LabelRendererBuilder<>("<div><span style=\"float:left;\">" + label
                + ":</span><div style=\"float:right;\">%s</div></div>", item);
    }
/*
    public static <T> LabelRendererBuilder<T> labelWithAlignRight(String label, ValueProvider<T, String> valueProvider) {
        return new LabelRendererBuilder<T>(s-> )
    }*/


}
