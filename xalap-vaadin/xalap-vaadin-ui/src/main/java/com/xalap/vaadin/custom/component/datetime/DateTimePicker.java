/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.datetime;

import com.xalap.framework.utils.DateHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 01/06/2019
 * @deprecated use instead {@link com.vaadin.flow.component.datetimepicker.DateTimePicker}
 */
@Deprecated
public class DateTimePicker
        extends AbstractDateTimePicker<Date> {

    public DateTimePicker(String label) {
        super(label);
    }

    @Override
    protected Date fromClientToServer(LocalDate date, LocalTime time) {
        return date != null && time != null ?
                DateHelper.date(LocalDateTime.of(date, time)) :
                null;
    }

    @Override
    protected LocalDateTime fromServerToClient(Date newPresentationValue) {
        return DateHelper.localDateTime(newPresentationValue);
    }

}
