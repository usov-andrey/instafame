/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;


import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

import static java.text.DateFormat.getDateInstance;

/**
 * @since 30.03.14
 * @deprecated use DateUtils
 */
@Deprecated(since = "1.0")
public class DateHelper {

    private DateHelper() {
    }

    public static Date date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime localDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate localDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String format(Date date, String style) {
        FastDateFormat format = FastDateFormat.getInstance(style);
        return format.format(date);
    }

	public static String getDateTime(Date date) {
		return format(date, "dd.MM.yyyy HH:mm");
	}

	public static String getDate(Date date) {
        return getDateInstance().format(date);
    }

    public static Date getDateTime(String dateTime, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date(LocalDateTime.parse(dateTime, formatter));
    }

    public static Date getDate(String date) {
        return getDateTime(date, "yyyy-MM-dd");
    }

    public static Date getDateTime(String date) {
        return getDateTime(date, "dd.MM.yyyy HH:mm");
    }

    /**
     * time - время в формате timeFormat
     */
    public static Date getDate(Date day, String timeString, String timeFormat) {
        Date time = getDateTime(timeString, timeFormat);
        return setTime(day, time);
    }


	public static Date clearTime(Date date) {
		return setTime(date, 0, 0);
	}

	public static Date incDays(Date date, int days) {
        return date(localDateTime(date).plusDays(days));
	}

	public static Date incHours(Date date, int hours) {
        return date(localDateTime(date).plusHours(hours));
	}

	public static Date incMinutes(Date date, int minutes) {
        return date(localDateTime(date).plusMinutes(minutes));
	}

    public static long secondsBetweenDates(Date time1, Date time2) {
        return Math.abs(ChronoUnit.SECONDS.between(localDateTime(time1), localDateTime(time2)));
    }

    public static long minutesBetweenDates(Date time1, Date time2) {
        return Math.abs(ChronoUnit.MINUTES.between(localDateTime(time1), localDateTime(time2)));
    }

    public static long daysBetweenDates(Date time1, Date time2) {
        return Math.abs(ChronoUnit.DAYS.between(localDateTime(time1), localDateTime(time2)));
    }

    public static long hoursBetweenDates(Date time1, Date time2) {
        return Math.abs(ChronoUnit.HOURS.between(localDateTime(time1), localDateTime(time2)));
    }

    public static Date setTime(Date date, int hours, int minutes) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date setTime(Date date, int minutes) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * @return для времени time задаем дату day
     */
    public static Date setTime(Date day, Date time) {
        Calendar calendar = calendar(time);
        return setTime(day, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
    }

    public static Integer getDayOfWeek(Date day) {
        Calendar calendar = calendar(day);
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    private static Calendar calendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date setDay(Date date, int dayOfMonth) {
        Calendar calendar = calendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return calendar.getTime();
    }

    public static String formatDuration(Date date1, Date date2) {
        return formatDuration(duration(date1, date2));
    }

    public static String formatDuration(long ms) {
        return DurationFormatUtils.formatDurationHMS(ms);
    }

    public static long duration(Date date1, Date date2) {
        return Math.abs(date1.getTime() - date2.getTime());
    }

    /**
     * Количество месяцев, прошедших между двумя датами.
     */
    public static int exactMonthBetweenDates(Date from, Date to) {
        return Period.between(localDate(from), localDate(to)).getMonths();
    }

    /**
     * Для быстрой работы с ms
     */
    public static class FixedDate extends Date {

        private final long ms;

        public FixedDate(Date date) {
            super(date.getTime());
            ms = date.getTime();
        }

        @Override
        public long getTime() {
            return ms;
        }
    }

}
