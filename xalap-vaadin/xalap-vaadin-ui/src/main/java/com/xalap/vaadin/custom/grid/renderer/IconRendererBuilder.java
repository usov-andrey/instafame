/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.grid.renderer;

import com.vaadin.flow.component.icon.VaadinIcon;
import com.xalap.vaadin.custom.grid.renderer.core.RendererBuilder;

/**
 * Рендерим иконку
 *
 * @author Usov Andrey
 * @since 2020-05-28
 */
public class IconRendererBuilder<T> extends RendererBuilder<T> {

    public IconRendererBuilder(VaadinIcon icon, String color, String sizeInPx) {
        super(String.format(
                "<vaadin-icon icon=\"vaadin:%s\" style=\"color:%s; width: %s; height: %s;\"></vaadin-icon>",
                iconName(icon), color, sizeInPx, sizeInPx)
        );
    }

    public static <T> IconRendererBuilder<T> icon(VaadinIcon icon, String color, String sizeInPx) {
        return new IconRendererBuilder<>(icon, color, sizeInPx);
    }

    private static String iconName(VaadinIcon icon) {
        return icon.name().toLowerCase().replace('_', '-');
    }

}
