/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.tilda;

/**
 * Обработчик запроса от Tilda
 *
 * @author Usov Andrey
 * @since 2020-04-09
 */
@FunctionalInterface
public interface TildaRequestHandler {

    /**
     * @param transactionId - В случае если tilda получает ошибку при обработке,
     *                      то она делает повторно этот же запрос еще раз.
     *                      У такого запроса будет тот же самый transactionId
     * @param form          - данные от формы Tilda
     */
    void handle(String transactionId, TildaForm form);
}
