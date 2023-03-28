/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api;

import com.xalap.framework.utils.DateHelper;

import java.util.Date;
import java.util.Objects;

/**
 * @author Усов Андрей
 * @since 31/05/2019
 */
public class InstagramAccount {

    private static final int WAIT_BLOCK_HOURS = 48;//Сколько часов ждать при блокировке

    private String login;
    private String password;
    private Date blockedDate;

    public InstagramAccount(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void block() {
        blockedDate = new Date();
    }

    /**
     * Заблокирован на время аккаунт или нет
     */
    public boolean isBlocked() {
        if (blockedDate != null) {
            if (DateHelper.hoursBetweenDates(blockedDate, new Date()) > WAIT_BLOCK_HOURS) {
                blockedDate = null;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", blockedDate=" + blockedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstagramAccount account = (InstagramAccount) o;
        return login.equals(account.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
