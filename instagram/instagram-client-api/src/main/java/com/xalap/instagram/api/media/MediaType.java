/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api.media;

/**
 * @author Усов Андрей
 * @since 21.07.17
 */
public enum MediaType {
    image("GraphImage", "Фото"), video("GraphVideo", "Видео"), sideCar("GraphSidecar", "Карусель");

    private final String typeName;
    private final String caption;

    MediaType(String typeName, String caption) {
        this.typeName = typeName;
        this.caption = caption;
    }

    public static MediaType value(String typeName) {
        for (MediaType mediaType : values()) {
            if (mediaType.typeName.equals(typeName)) {
                return mediaType;
            }
        }

        throw new IllegalStateException("Not found media type by:" + typeName);
    }

    public String getCaption() {
        return caption;
    }
}
