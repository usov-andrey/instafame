/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.component.datetime;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Usov Andrey
 * @since 2020-04-22
 */
public class InstantPicker extends AbstractDateTimePicker<Instant> {

    public InstantPicker(String label) {
        super(label);
    }

    @Override
    protected Instant fromClientToServer(LocalDate date, LocalTime time) {
        return date.atTime(time).atZone(clientZone()).toInstant();
    }

    @Override
    protected LocalDateTime fromServerToClient(Instant newPresentationValue) {
        return newPresentationValue.atZone(clientZone()).toLocalDateTime();
    }
}
