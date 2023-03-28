/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.crm.service.order.OrderBean;

/**
 * @author Usov Andrey
 * @since 18.06.2021
 */
public class CreateIOBean {

    private final OrderBean orderBean;
    private final IOFollowersSpeed followersSpeed;
    private final String comments;

    public CreateIOBean(OrderBean orderBean, IOFollowersSpeed followersSpeed, String comments) {
        this.orderBean = orderBean;
        this.followersSpeed = followersSpeed;
        this.comments = comments;
    }

    public OrderBean getOrderBean() {
        return orderBean;
    }

    public IOFollowersSpeed getFollowersSpeed() {
        return followersSpeed;
    }

    public String getComments() {
        return comments;
    }
}
