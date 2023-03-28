/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.api;

/**
 * Статус платежа
 *
 * @author Usov Andrey
 * @since 2020-04-17
 */
public enum PaymentStatus {
    /**
     * После получения платежа
     */
    created,
    /**
     * После обработки платежа бизнес логиков
     */
    processed
}
