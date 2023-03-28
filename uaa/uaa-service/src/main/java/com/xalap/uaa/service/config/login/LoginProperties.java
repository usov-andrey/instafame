/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.config.login;

/**
 * Настройки входа
 *
 * @author Usov Andrey
 * @since 04.04.2022
 */
public class LoginProperties {

    private String successUrl = "/";
    private String page = "login";
    private LoginMode mode = LoginMode.FORM;

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public LoginMode getMode() {
        return mode;
    }

    public void setMode(LoginMode mode) {
        this.mode = mode;
    }

}
