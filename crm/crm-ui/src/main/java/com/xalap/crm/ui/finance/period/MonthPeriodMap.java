/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance.period;

import java.time.LocalDate;

/**
 * @author Usov Andrey
 * @since 2020-10-17
 */
public class MonthPeriodMap<T> extends PeriodMap<T> {

    @Override
    protected StartPeriod startPeriod(LocalDate localDate) {
        localDate = localDate.withDayOfMonth(1);
        LocalDate nextMonth = localDate.plusMonths(1);
        return new StartPeriod(localDate, date -> date.isBefore(nextMonth));
    }
}
