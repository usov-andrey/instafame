/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.dto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Конвертирует D в T и обратно
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
public interface DtoMapper<D, T> {

    /**
     * Из T в D конвертирует
     */
    D toDto(T object);

    /**
     * Из D в T конвертирует
     */
    T fromDto(D dto);

    default Optional<D> toDto(Optional<T> optionalObject) {
        return optionalObject.map(this::toDto);
    }

    default List<D> toDto(List<T> objects) {
        return objects.stream().map(this::toDto).collect(Collectors.toList());
    }
}
