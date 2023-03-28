/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.http.responsemodel.media.CommentNode;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 14.03.2018
 */
public class Comment {

    private String userName;
    private Date time;
    private String text;

    public Comment() {
    }

    public Comment(CommentNode node) {
        userName = node.getOwner().getUsername();
        time = new Date(node.getCreatedAt() * 1000L);
        text = node.getText();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "userName='" + userName + '\'' +
                ", time=" + time +
                ", text='" + text + '\'' +
                '}';
    }
}
