/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.filter;

import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;

import java.time.LocalDate;

/**
 * @author Usov Andrey
 * @since 2020-08-26
 */
public class PeriodFilter implements Filter {

    public static final String FROM = "from";
    public static final String TO = "to";


    @FieldName(FROM)
    @Caption("С")
    private LocalDate from;

    @FieldName(TO)
    @Caption("По")
    private LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }

    @Override
    public boolean empty() {
        return from == null && to == null;
    }
}
