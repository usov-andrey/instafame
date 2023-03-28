/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.handler;

import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
public interface ChangeHandler extends Serializable {

    void onChange();

}
