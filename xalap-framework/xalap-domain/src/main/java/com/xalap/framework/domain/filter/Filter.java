/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.filter;

import java.io.Serializable;

/**
 * Фильтр в гриде
 *
 * @author Usov Andrey
 * @since 2020-04-10
 */
public interface Filter extends Serializable {

    boolean empty();

}
