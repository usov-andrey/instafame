/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder.tasklist;

import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.task.IOTaskSpeed;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;

import java.io.Serializable;

/**
 * @author Усов Андрей
 * @since 18/04/2019
 */
public class CreateTaskBean implements Serializable {

    private final IOBean orderBean;
    private InstaOrderTaskType taskType;
    private CheatTaskBean cheatTaskBean;
    private Integer quantity;
    private String urls;
    private IOTaskSpeed speed;
    private String commentText;

    public CreateTaskBean(IOBean orderBean) {
        this.orderBean = orderBean;
    }

    public IOBean getOrderBean() {
        return orderBean;
    }

    public InstaOrderTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(InstaOrderTaskType taskType) {
        this.taskType = taskType;
    }

    public CheatTaskBean getCheatTaskBean() {
        return cheatTaskBean;
    }

    public void setCheatTaskBean(CheatTaskBean cheatTaskBean) {
        this.cheatTaskBean = cheatTaskBean;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUrls() {
        return urls;
    }

    public CreateTaskBean setUrls(String urls) {
        this.urls = urls;
        return this;
    }

    public IOTaskSpeed getSpeed() {
        return speed;
    }

    public void setSpeed(IOTaskSpeed speed) {
        this.speed = speed;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}
