/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.search;

/**
 * @author Усов Андрей
 * @since 03.10.2018
 */
public class InstagramUserDTO {

    protected String value;
    protected String label;
    private final String picUrl;
    private final boolean isPrivate;

    public InstagramUserDTO(String userName, String fullName, String picUrl, Boolean isPrivate) {
        this.value = userName;
        this.label = fullName;
        this.picUrl = picUrl;
        this.isPrivate = isPrivate;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "InstagramUser{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
