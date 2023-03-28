/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Не может быть сериализовано из-за того, что хранится информация о классе Field
 *
 * @author Усов Андрей
 * @since 18/04/2019
 */
public class BeanAnnotationInfo {

    private final Map<String, FieldAnnotationInfo> fields = new LinkedHashMap<>();
    private final Map<String, FieldAnnotationInfo> fieldsByPropertyName = new LinkedHashMap<>();

    public boolean isExists(FieldAnnotationInfo info) {
        return fieldsByPropertyName.containsKey(info.getKey());
    }

    public void addField(FieldAnnotationInfo fieldAnnotationInfo) {
        String fieldName = fieldAnnotationInfo.getKey();
        fields.put(fieldName, fieldAnnotationInfo);
        fieldsByPropertyName.put(fieldAnnotationInfo.getName(), fieldAnnotationInfo);

    }

    public FieldAnnotationInfo getField(String fieldName) {
        FieldAnnotationInfo fieldAnnotationInfo = fields.get(fieldName);
        if (fieldAnnotationInfo == null) {
            throw new IllegalArgumentException("Not found field by fieldName:" + fieldName + " in fields:" + fields);
        }
        return fieldAnnotationInfo;
    }

    @Override
    public String toString() {
        return "BeanAnnotationInfo{" +
                "fields=" + fields +
                '}';
    }

    public Collection<FieldAnnotationInfo> getFields() {
        return fields.values();
    }
}
