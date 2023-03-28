/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.datetime;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.xalap.framework.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

/**
 * Абстракный класс для полей типа Дата+Время
 *
 * @author Usov Andrey
 * @since 2020-04-22
 */
public abstract class AbstractDateTimePicker<T> extends CustomField<T> {

    private final DatePicker datePicker = new DatePicker();
    private final TimePicker timePicker = new TimePicker();

    protected AbstractDateTimePicker(String label) {
        setLabel(label);
        datePicker.setWidth("150px");
        timePicker.setWidth("80px");
        add(datePicker, new Label(" "), timePicker);
        timePicker.addValueChangeListener(event -> updateValue());
    }

    @Override
    protected T generateModelValue() {
        final LocalDate date = datePicker.getValue();
        final LocalTime time = timePicker.getValue();
        return fromClientToServer(date, time);
    }

    protected abstract T fromClientToServer(LocalDate date, LocalTime time);

    @Override
    protected void setPresentationValue(
            T newPresentationValue) {
        if (newPresentationValue != null) {
            LocalDateTime localDateTime = fromServerToClient(newPresentationValue);
            datePicker.setValue(localDateTime.toLocalDate());
            timePicker.setValue(localDateTime.toLocalTime());
        }

    }

    /**
     * Из базы отдаем значение на клиент
     */
    protected abstract LocalDateTime fromServerToClient(T newPresentationValue);

    protected ZoneId clientZone() {
        return DateUtils.serverZone();
    }

    protected ZoneId serverZone() {
        return DateUtils.serverZone();
    }
}
