/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.field;

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Setter;
import com.vaadin.flow.function.ValueProvider;
import com.xalap.framework.utils.DateHelper;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author Usov Andrey
 * @since 2020-06-04
 */
public class DateFieldCreator implements FieldCreator<Date, LocalDateTime, DateTimePicker> {

    @Override
    public <Bean> void bind(Binder.BindingBuilder<Bean, LocalDateTime> bindingBuilder, ValueProvider<Bean, Date> valueProvider, Setter<Bean, Date> setter) {
        bindingBuilder.bind(bean -> {
            Date date = valueProvider.apply(bean);
            return date != null ? DateHelper.localDateTime(date) : null;
        }, (bean, localDateTime) -> {
            if (localDateTime != null) {
                Date date = DateHelper.date(localDateTime);
                setter.accept(bean, date);
            }
        });
    }

    @Override
    public DateTimePicker createField(String caption) {
        return new DateTimePicker(caption);
    }
}
