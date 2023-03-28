/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

/**
 * Данные об акаунте привязанные ко времени получения этих данных
 *
 * @author Усов Андрей
 * @since 23.04.17
 */
public class AccountData extends Data<Account> {

    public AccountData() {
    }

    public AccountData(Account object) {
        super(object);
    }

}
