/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.task;

/**
 * @author Андрей
 * @since 09.01.16
 */
public class TaskRunException extends RuntimeException {

	public TaskRunException(String message, Throwable cause) {
		super(message, cause);
	}

	public TaskRunException(Throwable cause) {
		super(cause);
	}
}
