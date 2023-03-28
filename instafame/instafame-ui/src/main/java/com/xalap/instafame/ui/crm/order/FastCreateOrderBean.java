/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.crm.order;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.instafame.service.instaorder.IOFollowersSpeed;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Usov Andrey
 * @since 2020-04-20
 */
public class FastCreateOrderBean implements Serializable {

    @NotNull
    private ContactBean contactBean;
    @NotNull
    private String email;
    @NotNull
    private String instagram;
    @NotNull
    private String orderText;//Премиум 800 подписчиков
    @NotNull
    private Double customerPay;
    @NotNull
    private Double fee;
    @NotNull
    private String invId;
    @NotNull
    private IOFollowersSpeed followersSpeed;

    public String getOrderText() {
        return orderText;
    }

    public void setOrderText(String orderText) {
        this.orderText = orderText;
    }

    public Double getCustomerPay() {
        return customerPay;
    }

    public void setCustomerPay(Double customerPay) {
        this.customerPay = customerPay;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public String getInvId() {
        return invId;
    }

    public void setInvId(String invId) {
        this.invId = invId;
    }

    public IOFollowersSpeed getFollowersSpeed() {
        return followersSpeed;
    }

    public void setFollowersSpeed(IOFollowersSpeed followersSpeed) {
        this.followersSpeed = followersSpeed;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public ContactBean getContactBean() {
        return contactBean;
    }

    public void setContactBean(ContactBean contactBean) {
        this.contactBean = contactBean;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
