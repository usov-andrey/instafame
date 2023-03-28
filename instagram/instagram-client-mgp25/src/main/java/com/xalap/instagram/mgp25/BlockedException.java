/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25;

import com.xalap.framework.exception.ServiceTemporaryException;
import org.slf4j.Logger;

/**
 * @author Usov Andrey
 * @since 2020-09-09
 */
public class BlockedException extends ServiceTemporaryException {

    public BlockedException(String error) {
        super(error);
    }

    @Override
    public void log(Logger log, String s) {
        log.warn(s, this);
    }
}
