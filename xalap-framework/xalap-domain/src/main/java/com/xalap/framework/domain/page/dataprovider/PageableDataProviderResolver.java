/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page.dataprovider;

import java.io.Serializable;

/**
 * @author Usov Andrey
 * @since 2020-04-29
 */
@FunctionalInterface
public interface PageableDataProviderResolver<T> extends Serializable {

    PageableDataProvider<T> resolve();

}
