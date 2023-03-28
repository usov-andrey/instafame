/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.function.SerializableFunction;
import com.vaadin.flow.function.SerializableSupplier;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.vaadin.custom.component.layout.CustomVerticalLayout;

import java.util.List;
import java.util.Map;

/**
 * Выводит список статусов обьектов с количеством для каждого статуса
 *
 * @author Усов Андрей
 * @since 09/05/2019
 */
public class EnumCountList<E extends Enum, T> extends CustomVerticalLayout {

    private final Class<E> enumClass;
    private final SerializableSupplier<List<T>> objectsSupplier;
    private final SerializableFunction<T, E> statusFunction;
    private final SerializableFunction<List<T>, Integer> numberFunction;

    public EnumCountList(Class<E> enumClass, SerializableSupplier<List<T>> objectsSupplier,
                         SerializableFunction<T, E> statusFunction, SerializableFunction<List<T>, Integer> numberFunction) {
        this.enumClass = enumClass;
        this.objectsSupplier = objectsSupplier;
        this.statusFunction = statusFunction;
        this.numberFunction = numberFunction;
    }

    public EnumCountList(Class<E> enumClass, SerializableSupplier<List<T>> objectsSupplier, SerializableFunction<T, E> statusFunction) {
        this(enumClass, objectsSupplier, statusFunction, List::size);
    }

    public void redraw() {
        removeAll();
        //Группируем по статусам
        Map<E, List<T>> map =
                CollectionHelper.createMultiMap(objectsSupplier.get(), statusFunction);
        setVisible(false);
        for (E status : enumClass.getEnumConstants()) {
            List<T> statusObjects = map.get(status);
            if (statusObjects != null) {
                add(new Label(status.name() + ": " + numberFunction.apply(statusObjects)));
                setVisible(true);
            }
        }
    }
}
