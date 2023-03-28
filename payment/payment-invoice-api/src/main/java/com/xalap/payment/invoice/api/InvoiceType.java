/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.invoice.api;

import java.io.Serializable;

/**
 * Тип счета
 *
 * @author Usov Andrey
 * @since 17.05.2021
 */
public interface InvoiceType extends Serializable {

    /**
     * @return имя обработчика платежа
     */
    String getHandler();

    /**
     * @return описание платежа для пользователя
     */
    String getDescription();
}
