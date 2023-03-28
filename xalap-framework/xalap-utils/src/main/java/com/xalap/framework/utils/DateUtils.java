/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Функции по работе с датами/временем
 *
 * @author Usov Andrey
 * @since 29/01/2020
 */
public class DateUtils {

    public static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_RU_PATTERN = "dd.MM.yyyy";
    private static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";

    private DateUtils() {
    }

    /**
     * @return строкое значение даты в формате dd.MM.yyyy, например, 05.08.2020
     */
    public static String formatWithRu(LocalDate localDate) {
        return format(DEFAULT_RU_PATTERN, localDate);
    }

    public static String formatWithISO8601(LocalDateTime localDateTime) {
        return format(ISO8601, localDateTime);
    }

    /**
     * @param pattern - шаблон, например, yyyy-MM-dd
     * @return строкое значение даты, например, 2019-03-29
     */
    public static String format(String pattern, LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String format(String pattern, LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * @param value строкое значение даты в формате dd.MM.yyyy, например, 05.08.2020
     */
    public static LocalDate parseDateWithRuFormat(String value) {
        return parseDate(DEFAULT_RU_PATTERN, value);
    }

    /**
     * @param pattern - шаблон, например, yyyy-MM-dd
     * @param value   - строкое значение даты, например, 2019-03-29
     */
    public static LocalDate parseDate(String pattern, String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(value, formatter);
    }
    /**
     * @return Часовой пояс, в котором производятся все серверные вычисления и в котором хранятся данные.
     * Используется конкретный часовой пояс, а не UTC для удобства
     */
    public static ZoneId serverZone() {
        return ZoneId.of("Europe/Moscow");
    }

    /**
     * @return Текущее время в серверном часовом поясе
     */
    public static LocalDateTime now() {
        return ZonedDateTime.now(serverZone()).toLocalDateTime();
    }

    /**
     * @param serverDateTime - время в серверном часовом поясе
     * @param clientZone     - клиентский часовой пояс
     * @return время в клиентском часовом поясе
     */
    public static LocalDateTime toClient(LocalDateTime serverDateTime, ZoneId clientZone) {
        return toZone(serverDateTime, serverZone(), clientZone);
    }

    /**
     * @param clientDateTime - время в клиентском часовом поясе
     * @param clientZone     - клиенсткий часовой пояс
     * @return время в серверном часовом поясе
     */
    public static LocalDateTime fromClient(LocalDateTime clientDateTime, ZoneId clientZone) {
        return toZone(clientDateTime, clientZone, serverZone());
    }

    public static Date toZone(Date time, final ZoneId fromZone, final ZoneId toZone) {
        return date(toZone(localDateTime(time), fromZone, toZone));
    }

    /**
     * @param time     Время в часовом поясе fromZone
     * @param fromZone Исходный часовой пояс
     * @param toZone   Конечный часовой пояс
     * @return Время в часовом поясе toZone
     */
    public static LocalDateTime toZone(final LocalDateTime time, final ZoneId fromZone, final ZoneId toZone) {
        final ZonedDateTime zonedTime = time.atZone(fromZone);
        final ZonedDateTime converted = zonedTime.withZoneSameInstant(toZone);
        return converted.toLocalDateTime();
    }

    public static Date date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDateTime localDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date date(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate localDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

}
