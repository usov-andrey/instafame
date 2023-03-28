/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.web;

/**
 * Список html страниц в uaa
 *
 * @author Usov Andrey
 * @since 2020-04-15
 */
public enum UaaHtmlPage {

    CHANGE_PASSWORD("change-password"), FORGOT_PASSWORD("forgot-password"),
    REGISTRATION("registration"), SUCCESS_REGISTRATION("success-registration");


    private final String htmlFileName;

    UaaHtmlPage(String htmlFileName) {
        this.htmlFileName = htmlFileName;
    }

    public String getHtmlFileName() {
        return htmlFileName;
    }
}
