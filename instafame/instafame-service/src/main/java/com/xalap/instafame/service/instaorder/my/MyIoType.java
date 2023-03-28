/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.my;

import com.xalap.framework.domain.holder.NameHolder;
import com.xalap.instafame.service.instaorder.IOType;

/**
 * @author Usov Andrey
 * @since 2020-05-14
 */
public enum MyIoType implements NameHolder {

    all("Все типы заказов") {
        @Override
        public IOType getType() {
            //Здесь возвращаем любое значение, так как isEmpty=true
            return IOType.vip;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }
    },
    vip("VIP") {
        @Override
        public IOType getType() {
            return IOType.vip;
        }
    },
    combined("Комплексные") {
        @Override
        public IOType getType() {
            return IOType.combined;
        }
    },
    followers("Подписчики") {
        @Override
        public IOType getType() {
            return IOType.followers;
        }
    },
    likes("Лайки") {
        @Override
        public IOType getType() {
            return IOType.likes;
        }
    },
    comments("Комментарии") {
        @Override
        public IOType getType() {
            return IOType.comments;
        }
    },
    views("Просмотры видео") {
        @Override
        public IOType getType() {
            return IOType.views;
        }
    };

    private final String name;

    MyIoType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    abstract public IOType getType();

    public boolean isEmpty() {
        return false;
    }
}
