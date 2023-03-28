/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.annotation;

import com.xalap.framework.spring.BeanFactory;
import com.xalap.framework.utils.ReflectHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
@Service
public class BeanAnnotationInfoProvider {

    private final Map<Class<?>, BeanAnnotationInfo> infoMap = new ConcurrentHashMap<>();

    public static BeanAnnotationInfoProvider getInstance() {
        return BeanFactory.get(BeanAnnotationInfoProvider.class);
    }

    public BeanAnnotationInfo getInfo(Class<?> beanClass) {
        if (!infoMap.containsKey(beanClass)) {
            infoMap.put(beanClass, create(beanClass));
        }
        return infoMap.get(beanClass);
    }

    private BeanAnnotationInfo create(Class<?> beanClass) {
        BeanAnnotationInfo info = new BeanAnnotationInfo();
        //Обрабатываем поля
        ReflectionUtils.doWithFields(beanClass, classField -> {
            ReflectHelper.getGetter(classField).ifPresent(getter -> {
                FieldAnnotationInfo fieldAnnotationInfo = new FieldAnnotationInfo(classField, getter);
                addField(info, fieldAnnotationInfo, classField.getAnnotations());
            });
        }, field -> !Modifier.isStatic(field.getModifiers()));
        //Возможно это интерфейс, тогда обрабатываем геттеры
        ReflectionUtils.doWithMethods(beanClass, method -> {
            if (ReflectHelper.isGetter(method)) {
                FieldAnnotationInfo fieldAnnotationInfo = new FieldAnnotationInfo(method);
                if (!info.isExists(fieldAnnotationInfo)) {
                    addField(info, fieldAnnotationInfo, method.getAnnotations());
                }
            }
        });
        return info;
    }

    private void addField(BeanAnnotationInfo info, FieldAnnotationInfo fieldAnnotationInfo, Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            processAnnotation(fieldAnnotationInfo, annotation);
        }
        info.addField(fieldAnnotationInfo);
    }

    private void processAnnotation(FieldAnnotationInfo fieldAnnotationInfo, Annotation annotation) {
        if (annotation instanceof Id) {
            fieldAnnotationInfo.setId(true);
        } else if (annotation instanceof Caption) {
            fieldAnnotationInfo.setCaption(((Caption) annotation).value());
        } else if (annotation instanceof FieldName) {
            fieldAnnotationInfo.setKey(((FieldName) annotation).value());
        } else if (annotation instanceof FieldImage) {
            fieldAnnotationInfo.setImage(((FieldImage) annotation).value());
        } else if (annotation instanceof Column) {
            int length = ((Column) annotation).length();
            fieldAnnotationInfo.setLength(length);
        } else if (annotation instanceof OneToOne || annotation instanceof ManyToOne) {
            fieldAnnotationInfo.setFk(true);
        }
    }
}
