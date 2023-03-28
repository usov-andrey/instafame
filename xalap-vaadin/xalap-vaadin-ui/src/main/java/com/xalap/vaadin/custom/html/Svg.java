/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.html;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.PropertyDescriptor;
import com.vaadin.flow.component.PropertyDescriptors;
import com.xalap.framework.utils.IOUtils;
import com.xalap.vaadin.custom.component.fluent.Span;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Из проблем с vaadin нужно оборачивать SVG в div/span
 *
 * Предпологается, что сами файлы svg будут располагаться по пути static/images/
 *
 * @author Usov Andrey
 * @since 2020-10-05
 */
public class Svg extends Span {

    public static final String SVG = "<svg ";
    public static final String ATT_EQUALS = "=\"";
    private static final PropertyDescriptor<String, String> innerHtmlDescriptor = PropertyDescriptors
            .propertyWithDefault("innerHTML", "");
    private static final String PATH = "/static/images/%s.svg";
    private final Map<String, String> attributes = new HashMap<>();
    private String name;

    public Svg(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Svg setName(String name) {
        this.name = name;
        return this;
    }

    public Svg size(int width, int height) {
        attributes.put("width", Integer.toString(width));
        attributes.put("height", Integer.toString(height));
        return this;
    }

    public Svg fill(String value) {
        attributes.put("fill", value);
        return this;
    }

    public Svg className(String className) {
        attributes.put("class", className);
        return this;
    }

    public boolean isAttributeEquals(String name, String value) {
        String o = attributes.get(name);
        return o != null && o.equals(value);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        update();
    }

    /**
     * Перерисовывает svg в соответствии с измененными аттрибутами
     */
    public Svg update() {
        set(innerHtmlDescriptor, createHtml());
        return this;
    }

    protected String createHtml() {
        String path = String.format(PATH, name);
        InputStream resourceAsStream = Svg.class.getResourceAsStream(path);
        if (resourceAsStream == null) {
            throw new IllegalStateException("Not found resource as path:" + path);
        }
        String html = IOUtils.toString(resourceAsStream);
        //Заменяем аттрибуты в svg
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            html = replaceAttValue(html, entry.getKey(), entry.getValue());
        }
        return html;
    }

    /**
     * Устанавливает размеры у div
     */
    public Svg divSize(String width, String height) {
        setWidth(width);
        setHeight(height);
        return this;
    }

    protected String replaceAttValue(String html, String attName, String value) {
        int index = html.indexOf(attName + ATT_EQUALS);
        if (index > -1) {
            int lastIndex = html.indexOf("\"", index + attName.length() + 2);
            html = html.substring(0, index)
                    + attName + "=\""
                    + value + html.substring(lastIndex);
        } else {
            //Иначе добавляем новый аттрибут
            html = SVG + attName + ATT_EQUALS + value + "\"" + html.substring(SVG.length() - 1);
        }
        return html;
    }

    public Svg divClass(String className) {
        setClassName(className);
        return this;
    }

    public Svg onClick(Runnable runnable) {
        addClickListener(event -> runnable.run());
        return this;
    }
}
