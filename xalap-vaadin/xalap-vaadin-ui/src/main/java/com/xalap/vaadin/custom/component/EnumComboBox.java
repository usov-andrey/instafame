/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component;

import com.vaadin.flow.component.combobox.ComboBox;
import com.xalap.framework.domain.holder.NameHolder;

import java.util.Arrays;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
public class EnumComboBox<E extends Enum> extends ComboBox<E> {

    public EnumComboBox(String caption, Class<E> enumClass) {
        super(caption, Arrays.asList(enumClass.getEnumConstants()));
        setItemLabelGenerator(NameHolder.class.isAssignableFrom(enumClass) ?
                e -> ((NameHolder) e).getName() : Enum::name);
    }
}
