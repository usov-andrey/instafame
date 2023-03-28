/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 04.05.17
 */
public class HashTagCategory {

    private String name;
    private List<String> tags;

    public HashTagCategory() {
    }

    public HashTagCategory(String name, List<String> tags) {
        this.name = name;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return name + "(" + tags.size() + ")";
    }
}
