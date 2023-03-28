/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.framework.utils.ReflectHelper;

import java.util.Date;

/**
 * Сохраняет время сохранения
 *
 * @author Усов Андрей
 * @since 23.04.17
 */
public class Data<V> {

    private V object;
    private Date time;

    public Data() {
    }

    public Data(V object) {
        this.object = object;
        this.time = new Date();
    }

    public static <T extends Data<V>, V> T create(Class<T> dataClass) {
        return ReflectHelper.newInstance(dataClass);
    }

    public static <T extends Data<V>, V> T create(Class<T> dataClass, V object) {
        T data = create(dataClass);
        data.setTime(new Date());
        data.setObject(object);
        return data;
    }

    public V getObject() {
        return object;
    }

    public void setObject(V object) {
        this.object = object;
    }

    public V object(Class<V> objectClass) {
        if (object == null) {
            object = ReflectHelper.newInstance(objectClass);
        }
        return object;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Data<V> time(Date time) {
        setTime(time);
        return this;
    }

    public boolean empty() {
        return object == null;
    }

    @Override
    public String toString() {
        return "Data{" +
                "object=" + object +
                ", time=" + time +
                '}';
    }
}
