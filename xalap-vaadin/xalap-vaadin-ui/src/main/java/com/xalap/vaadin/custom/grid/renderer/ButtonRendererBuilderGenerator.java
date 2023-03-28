/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.TextNode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

/**
 * @author Usov Andrey
 * @since 2020-05-04
 * Usage of code https://github.com/Legioth
 * Modified version of https://vaadin.com/directory/component/button-renderer
 */
public class ButtonRendererBuilderGenerator<T> {

    private final SerializableConsumer<T> clickHandler;
    private ValueProvider<T, String> captionGenerator;
    private ValueProvider<T, Icon> prefixIconGenerator;
    private ValueProvider<T, Icon> suffixIconGenerator;
    private SerializablePredicate<T> enabledGenerator;
    private ValueProvider<T, String> styleGenerator;
    private String caption;
    private VaadinIcon suffixIcon;
    private VaadinIcon prefixIcon;
    private String style;

    /**
     * Creates a renderer builder that will use the provided click handler.
     *
     * @param clickHandler a consumer that will receive the clicked item, not
     *                     <code>null</code>
     */
    public ButtonRendererBuilderGenerator(SerializableConsumer<T> clickHandler) {
        this.clickHandler = Objects.requireNonNull(clickHandler);
    }

    /**
     * Creates a button renderer with the given caption generator and click
     * handler
     *
     * @param captionGenerator the caption generator to use, not <code>null</code>
     * @param clickHandler     the click handler to use, not <code>null</code>
     * @return the created renderer, not <code>null</code>
     */
    public static <T> RendererBuilder<T> button(ValueProvider<T, String> captionGenerator,
                                                SerializableConsumer<T> clickHandler) {
        return new ButtonRendererBuilderGenerator<>(clickHandler)
                .withCaption(Objects.requireNonNull(captionGenerator, "Caption generator cannot be null")).build();
    }


    public static <T> RendererBuilder<T> button(String caption, SerializableConsumer<T> clickHandler) {
        return new ButtonRendererBuilderGenerator<>(clickHandler)
                .withCaption(Objects.requireNonNull(caption, "Caption cannot be null")).build();
    }

    private static <T> String createIconTag(String slot, String propertySuffix, ValueProvider<T, Icon> dynamicValue,
                                            VaadinIcon staticValue, Map<String, ValueProvider<T, ?>> properties) {
        return "<vaadin-icon slot=" + slot + " " + createAttribute("icon", slot + "icon", propertySuffix,
                item -> dynamicValue.apply(item).getElement().getAttribute("icon"),
                staticValue == null ? null : "vaadin:" + staticValue.name().toLowerCase().replace('_', '-'), properties)
                + "></vaadin-icon>";
    }

    private static <T> String createAttribute(String attributeName, String propertyName, String propertySuffix,
                                              ValueProvider<T, ?> dynamicValue, String staticValue, Map<String, ValueProvider<T, ?>> properties) {
        String value;
        if (staticValue != null) {
            value = staticValue;
            if (attributeName.endsWith("$")) {
                attributeName = attributeName.substring(0, attributeName.length() - 1);
            }
        } else if (dynamicValue != null) {
            value = "{{item." + propertyName + propertySuffix + "}}";
            properties.put(propertyName, dynamicValue);
        } else {
            throw new IllegalArgumentException("Either dynamic or static value must be provided");
        }

        return new Attribute(attributeName, value).html();
    }

    /**
     * Creates a renderer based on the current configuration of this builder.
     *
     * @return a new renderer, not <code>null</code>
     */
    public RendererBuilder<T> build() {
        if (prefixIconGenerator == null && prefixIcon == null && captionGenerator == null && caption == null
                && suffixIconGenerator == null && suffixIcon == null) {
            throw new IllegalStateException("Must configure at least one icon or a caption");
        }

        HashSet<Class<? extends Component>> usedComponents = new HashSet<>();
        usedComponents.add(Button.class);

        // Random suffix to reduce the risk of conflicts if multiple button
        // renderers are used in the same grid
        String propertySuffix = "_" + RendererBuilder.createRandomSuffix();
        Map<String, ValueProvider<T, ?>> properties = new HashMap<>();

        StringBuilder template = new StringBuilder("<vaadin-button on-click='clickHandler" + propertySuffix + "'");

        if (captionGenerator == null && caption == null) {
            // Only icons but no text
            template.append(" theme=icon");
        }
        if (enabledGenerator != null) {
            template.append(" disabled='{{!item.enabled").append(propertySuffix).append("}}'");
            properties.put("enabled", enabledGenerator::test);
        }
        if (styleGenerator != null || style != null) {
            template.append(" ")
                    .append(createAttribute("style$", "style", propertySuffix, styleGenerator, style, properties));
        }
        template.append(">");

        if (prefixIconGenerator != null || prefixIcon != null) {
            usedComponents.add(Icon.class);

            template.append(createIconTag("prefix", propertySuffix, prefixIconGenerator, prefixIcon, properties));
        }
        if (captionGenerator != null) {
            template.append("{{item.caption").append(propertySuffix).append("}}");

            properties.put("caption", captionGenerator);
        }
        if (caption != null) {
            // Use TextNode to escape HTML
            template.append((new TextNode(caption)).toString());
        }
        if (suffixIconGenerator != null || suffixIcon != null) {
            usedComponents.add(Icon.class);

            template.append(createIconTag("suffix", propertySuffix, suffixIconGenerator, suffixIcon, properties));
        }

        template.append("</vaadin-button>");

        RendererBuilder<T> rendererItem = new RendererBuilder<>(template.toString(), usedComponents);
        rendererItem.withProperties(properties);

        rendererItem.withEventHandler("clickHandler" + propertySuffix, item -> {
            boolean alwaysEnabled = enabledGenerator == null;

            if (alwaysEnabled || enabledGenerator.test(item)) {
                clickHandler.accept(item);
            }
        });

        return rendererItem;
    }

    /**
     * Configures this builder to use the provided caption generator.
     *
     * @param captionGenerator caption generator to use, or <code>null</code> to not show any
     *                         caption in the buttons
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withCaption(ValueProvider<T, String> captionGenerator) {
        caption = null;
        this.captionGenerator = captionGenerator;
        return this;
    }

    /**
     * Configures this builder to use the provided caption.
     *
     * @param caption caption string to use, not <code>null</code>
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withCaption(String caption) {
        this.caption = Objects.requireNonNull(caption);
        captionGenerator = null;
        return this;
    }

    /**
     * Configures this builder to use the provided generator for prefix icons.
     *
     * @param prefixIconGenerator the prefix icon generator to use, or <code>null</code> to not
     *                            use an prefix icon
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withIconPrefix(ValueProvider<T, Icon> prefixIconGenerator) {
        this.prefixIconGenerator = prefixIconGenerator;
        prefixIcon = null;
        return this;
    }

    /**
     * Configures this builder to use the provided prefix icon.
     *
     * @param prefixIcon the prefix icon to use, not <code>null</code>
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withIconPrefix(VaadinIcon prefixIcon) {
        this.prefixIcon = Objects.requireNonNull(prefixIcon, "Prefix icon cannot be null");
        prefixIconGenerator = null;
        return this;
    }

    /**
     * Configures this builder to use the provided generator for suffix icons.
     *
     * @param suffixIconProvider the suffix icon generator to use, or <code>null</code> to not
     *                           use a suffix icon
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withIconSuffix(ValueProvider<T, Icon> suffixIconProvider) {
        this.suffixIconGenerator = suffixIconProvider;
        suffixIcon = null;
        return this;
    }

    /**
     * Configures this builder to use the provided suffix icon.
     *
     * @param suffixIcon the suffix icon to use, not <code>null</code>
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withIconSuffix(VaadinIcon suffixIcon) {
        this.suffixIcon = Objects.requireNonNull(suffixIcon, "Suffix icon cannot be null");
        suffixIconGenerator = null;
        return this;
    }

    /**
     * Configures this builder to use the provided enabled generator. If an
     * enabled generator is used, then rendered buttons will be enabled and
     * clickable only for data items for which the generator returns
     * <code>true</code>. All buttons are enabled and clickable if no generator
     * is defined.
     *
     * @param enabledGenerator the enabled generator to use, or <code>null</code> to not use
     *                         an enabled generator
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withEnabledGenerator(SerializablePredicate<T> enabledGenerator) {
        this.enabledGenerator = enabledGenerator;
        return this;
    }

    /**
     * Configures this builder to use the provided style generator. The value
     * produced by the style generator will be used as an inline style of the
     * button, i.e. as a <code>style</code> attribute.
     *
     * @param styleGenerator the style generator to use, or <code>null</code> to not use
     *                       any style generator
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withStyle(ValueProvider<T, String> styleGenerator) {
        this.styleGenerator = styleGenerator;
        this.style = null;
        return this;
    }

    /**
     * Configures this builder to use the provided style. The style value will
     * be used as an inline style of the button, i.e. as a <code>style</code>
     * attribute.
     *
     * @param style the style to use, not <code>null</code>
     * @return this builder, for chaining
     */
    public ButtonRendererBuilderGenerator<T> withStyle(String style) {
        this.style = Objects.requireNonNull(style, "Style cannot be null");
        styleGenerator = null;
        return this;
    }

}
