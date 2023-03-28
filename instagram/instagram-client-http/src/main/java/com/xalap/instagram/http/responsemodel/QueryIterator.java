/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import java.util.Iterator;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 21.04.17
 */
public interface QueryIterator<T> extends Iterator<List<T>> {

    /**
     * @return идентификатор последнего элемента на странице, чтобы получить следующую страницу со следующего элемента
     */
    String getLastObjectId();

}
