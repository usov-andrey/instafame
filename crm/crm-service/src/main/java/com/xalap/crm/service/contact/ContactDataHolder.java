/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

/**
 * @author Usov Andrey
 * @since 2020-06-03
 */
public interface ContactDataHolder {

    String getName();

    String getEmail();

    String getInstagram();

    /**
     * Хранит googleClientId
     */
    String getClientId();

}
