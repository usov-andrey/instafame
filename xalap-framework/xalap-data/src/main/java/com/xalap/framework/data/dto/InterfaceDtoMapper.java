/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.data.dto;

/**
 * Использовать, когда Dto - это интерфейс, а реализация T
 *
 * @author Usov Andrey
 * @since 15.05.2021
 */
public class InterfaceDtoMapper<D, T> implements DtoMapper<D, T> {

    @Override
    public D toDto(T object) {
        return ((D) object);
    }

    @Override
    public T fromDto(D dto) {
        return ((T) dto);
    }

}
