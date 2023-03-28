/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance.period;

import com.vaadin.flow.function.SerializableFunction;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.utils.DateHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * @author Усов Андрей
 * @since 07/01/2020
 */
public class StartPeriod implements Serializable {

    public static final String START_F = "start";

    @Caption("Период")
    @FieldName(START_F)
    private final LocalDate start;
    private final SerializableFunction<LocalDate, Boolean> inPeriodFunction;

    public StartPeriod(LocalDate start, SerializableFunction<LocalDate, Boolean> inPeriodFunction) {
        this.start = start;
        this.inPeriodFunction = inPeriodFunction;
    }

    public LocalDate getStart() {
        return start;
    }

    public boolean inPeriod(LocalDate date) {
        return !date.isBefore(start) && inPeriodFunction.apply(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StartPeriod that = (StartPeriod) o;

        return start != null ? start.equals(that.start) : that.start == null;

    }

    @Override
    public int hashCode() {
        return start != null ? start.hashCode() : 0;
    }

    public String startString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL", Locale.getDefault());
        return dateFormat.format(DateHelper.date(getStart()));
    }
}
