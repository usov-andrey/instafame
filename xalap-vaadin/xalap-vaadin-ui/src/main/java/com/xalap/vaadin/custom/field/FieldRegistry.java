/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.field;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.xalap.framework.domain.annotation.FieldAnnotationInfo;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.framework.utils.ReflectHelper;
import com.xalap.vaadin.custom.component.datetime.InstantPicker;
import com.xalap.vaadin.custom.dialog.ChoosingListDialog;
import com.xalap.vaadin.custom.frame.RootEntityFrame;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Маппинг, с помошщью каких компонентов нужно отображать поля бина определенного типа.
 * Например, поле LocalDate startDate будет отображаться с помощью DatePicker
 *
 * @author Усов Андрей
 * @since 28/06/2019
 */
@Service
public class FieldRegistry {

    //Для класса бина храним для каждого поля какой-то специфичный FieldCreator
    private final Map<Class, Map<String, FieldCreator>> fieldCreatorByFieldNameMap = new HashMap<>();
    //Для типа поля храним FieldCreator по-умолчанию
    private final Map<Class, FieldCreator> fieldCreatorByClassMap = new HashMap<>();

    @PostConstruct
    public void init() {
        add(new SimpleFieldCreator<>(s -> {
            DatePicker datePicker = new DatePicker(s);
            datePicker.setClearButtonVisible(true);
            return datePicker;
        }), LocalDate.class);
        add(new DateFieldCreator(), Date.class);
        add(new SimpleFieldCreator<>(InstantPicker::new), Instant.class);
        add(new SimpleFieldCreator<>(DateTimePicker::new), LocalDateTime.class);
        add(new SimpleFieldCreator<>(NumberField::new), Double.class, double.class);
        add(new IntegerFieldCreator(), Integer.class, int.class);
        add(new LongFieldCreator(), Long.class, long.class);
        add(new SimpleFieldCreator<>(BigDecimalField::new), BigDecimal.class);
        add(new SimpleFieldCreator<>(Checkbox::new), Boolean.class, boolean.class);
    }

    public void add(FieldCreator fieldCreator, Class... fieldClasses) {
        for (Class fieldClass : fieldClasses) {
            fieldCreatorByClassMap.put(fieldClass, fieldCreator);
        }
    }

    public void addForBean(FieldCreator fieldCreator, Class beanClass, String... fieldNames) {
        Map<String, FieldCreator> map = CollectionHelper.getHashMapOrCreate(fieldCreatorByFieldNameMap, beanClass);
        for (String fieldName : fieldNames) {
            map.put(fieldName, fieldCreator);
        }
    }

    public <ClassFieldType, FType, F extends Component & HasValue<?, FType>> FieldCreator<ClassFieldType, FType, F>
    get(FieldAnnotationInfo fieldAnnotationInfo) {
        //Вначале ищем FieldCreator для конкретного поля, если не найден, то ищем по классу
        Map<String, FieldCreator> map = fieldCreatorByFieldNameMap.get(fieldAnnotationInfo.getDeclaringClass());
        if (map != null) {
            FieldCreator fieldCreator = map.get(fieldAnnotationInfo.getKey());
            if (fieldCreator != null) {
                return fieldCreator;
            }
        }
        return fieldCreatorByClassMap.get(fieldAnnotationInfo.getType());
    }

    public <Key extends Comparable<Key>, Bean extends IdHolderWithName<Key>, GridBean extends IdHolder<Key>, Dialog extends ChoosingListDialog<GridBean, Bean>> void
    addLookup(Class<? extends RootEntityFrame<Bean, Key>> entityFrameClass, Supplier<Dialog> dialog) {
        Class genericParameterClass = ReflectHelper.getGenericParameterClass(entityFrameClass, RootEntityFrame.class, 0);
        add(new LookupFieldCreator<>(entityFrameClass, dialog), genericParameterClass);
    }
}
