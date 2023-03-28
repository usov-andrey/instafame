/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.framework.domain.holder.NameHolder;

/**
 * @author Usov Andrey
 * @since 2020-05-15
 */
public enum IOType implements NameHolder {

    vip("VIP"),
    combined("Комплексный"),
    followers("Подписчики"),
    likes("Лайки"),
    comments("Комментарии"),
    views("Просмотры видео");

    private final String name;

    IOType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
