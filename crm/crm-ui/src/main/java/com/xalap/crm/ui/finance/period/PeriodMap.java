/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.ui.finance.period;

import com.xalap.framework.utils.DateHelper;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

/**
 * @author Усов Андрей
 * @since 14/01/2020
 */
public abstract class PeriodMap<T> extends HashMap<StartPeriod, T> {

    public T computeIfAbsent(LocalDate time, Function<StartPeriod, T> beanCreator) {
        StartPeriod start = startPeriod(time);
        return computeIfAbsent(start, date -> beanCreator.apply(start));
    }

    public T computeIfAbsent(Date time, Function<StartPeriod, T> beanCreator) {
        StartPeriod start = startPeriod(time);
        return computeIfAbsent(start, date -> beanCreator.apply(start));
    }

    private StartPeriod startPeriod(Date time) {
        LocalDate localDate = DateHelper.localDate(time);
        return startPeriod(localDate);
    }

    abstract protected StartPeriod startPeriod(LocalDate localDate);

    public Collection<T> toList(Function<T, StartPeriod> periodFunction) {
        List<T> result = new ArrayList<>(values());
        result.sort(Comparator.comparing(o -> periodFunction.apply(o).getStart()));
        return result;
    }
}
