/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page.request;

import org.springframework.core.convert.converter.Converter;

/**
 * Конвертация PageRequest из строки
 *
 * @author Usov Andrey
 * @since 24.03.2022
 */
public class PageRequestConverter implements Converter<String, PageRequest> {

    @Override
    public PageRequest convert(String source) {
        return null;
    }
}
