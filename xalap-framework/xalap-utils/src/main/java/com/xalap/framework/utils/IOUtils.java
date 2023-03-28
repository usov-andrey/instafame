/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import com.xalap.framework.exception.IORuntimeException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Стандартная обработка IOException
 *
 * @author Usov Andrey
 * @since 2020-08-27
 */
public class IOUtils {

    public static String toString(InputStream inputStream) {
        try {
            return org.apache.commons.io.IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new IORuntimeException("Error on inputStream.toString:" + inputStream, e);
        }
    }
}
