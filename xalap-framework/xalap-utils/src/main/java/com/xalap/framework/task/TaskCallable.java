/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * Чтобы работало с Ignite нужно наследовать от их интерфейсов
 *
 * @author Андрей
 * @since 10.11.15
 */
public interface TaskCallable<V> extends Callable<V>, Serializable {
}
