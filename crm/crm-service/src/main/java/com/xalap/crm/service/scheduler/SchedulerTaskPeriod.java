/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.scheduler;

import com.xalap.framework.utils.DateHelper;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
public enum SchedulerTaskPeriod {

    one_time {
        @Override
        public long getTime(Integer value) {
            return T_ONE_TIME;
        }
    },
    every_n_minutes {
        @Override
        public long getTime(Integer value) {
            return T_MINUTE * value;
        }

        @Override
        public Date addToDate(Date date, Integer value) {
            return DateHelper.incMinutes(date, value);
        }
    },
    every_hour {
        @Override
        public long getTime(Integer value) {
            return T_HOUR;
        }

        @Override
        public Date addToDate(Date date, Integer value) {
            return DateHelper.incHours(date, 1);
        }
    },
    every_day {
        @Override
        public long getTime(Integer value) {
            return T_DAY;
        }

        @Override
        public Date addToDate(Date date, Integer value) {
            return DateHelper.incDays(date, 1);
        }
    },
    every_n_days {
        @Override
        public long getTime(Integer value) {
            return T_DAY * value;
        }

        @Override
        public Date addToDate(Date date, Integer value) {
            return DateHelper.incDays(date, value);
        }
    },
    every_week {
        @Override
        public long getTime(Integer value) {
            return T_WEEK;
        }

        @Override
        public Date addToDate(Date date, Integer value) {
            return DateHelper.incDays(date, 7);
        }
    },
    every_day_of_month {
        //У месяцов могут быть разные периоды времени
        @Override
        public long getTime(Integer value) {
            return 0;
        }
    };

    public static final long T_ONE_TIME = 0;
    public static final long T_MINUTE = 1000 * 60;
    public static final long T_HOUR = T_MINUTE * 60;
    public static final long T_DAY = T_HOUR * 24;
    public static final long T_WEEK = T_DAY * 7;


    abstract public long getTime(Integer value);

    public Date addToDate(Date date, Integer value) {
        return date;
    }

    public String getValue() {
        return String.valueOf(ordinal());
    }

}
