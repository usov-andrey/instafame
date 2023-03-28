/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

/**
 * Ссылка на обьект
 *
 * @author Андрей
 * @since 07.09.15
 */
public class Holder<T> {

	private T object;

    public Holder() {
    }

    public Holder(T object) {
        this.object = object;
    }

    public T get() {
		return object;
	}

	public void set(T object) {
		this.object = object;
	}
}
