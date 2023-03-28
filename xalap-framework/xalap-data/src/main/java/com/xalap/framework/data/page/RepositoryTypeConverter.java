/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.page;

import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Usov Andrey
 * @since 2020-04-30
 */
public interface RepositoryTypeConverter {

    @Deprecated
    /*
     * Для хранения в базе нужно использовать LocalDateTime или Instant
     */
    default Date startDate(LocalDate date) {
        return date != null ? DateHelper.date(start(date)) : null;
    }

    @Deprecated
    /*
     * Для хранения в базе нужно использовать LocalDateTime или Instant
     */
    default Date endDate(LocalDate date) {
        return date != null ? DateHelper.date(end(date)) : null;
    }

    default LocalDateTime start(LocalDate date) {
        return date != null ? date.atTime(LocalTime.MIN) : null;
    }

    default LocalDateTime end(LocalDate date) {
        return date != null ? date.atTime(LocalTime.MAX) : null;
    }

    default boolean empty(String value) {
        return StringHelper.isEmpty(value);
    }

    default boolean empty(Object object) {
        return object == null;
    }

    default <T> List<T> in(T... values) {
        return Arrays.asList(values);
    }

    default Pageable pageable(PageRequest pageRequest) {
        //Переводим список сортировки
        List<Sort.Order> orderList = pageRequest.getSort().getOrders().stream()
                .map(sortOrder -> new Sort.Order(
                        sortOrder.getDirection().isAscending() ?
                                Sort.Direction.ASC : Sort.Direction.DESC,
                        sortOrder.getProperty())).collect(Collectors.toList());

        return org.springframework.data.domain.PageRequest.of(pageRequest.getPage(), pageRequest.getSize(),
                Sort.by(orderList));
    }

}
