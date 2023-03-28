/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.datetime;

import com.xalap.framework.utils.DateUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Usov Andrey
 * @since 2020-04-22
 */
public class LocalDateTimePicker extends AbstractDateTimePicker<LocalDateTime> {

    public LocalDateTimePicker(String label) {
        super(label);
    }

    @Override
    protected LocalDateTime fromClientToServer(LocalDate date, LocalTime time) {
        return DateUtils.toZone(LocalDateTime.of(date, time), clientZone(), serverZone());
    }

    @Override
    protected LocalDateTime fromServerToClient(LocalDateTime newPresentationValue) {
        return DateUtils.toZone(newPresentationValue, serverZone(), clientZone());
    }
}
