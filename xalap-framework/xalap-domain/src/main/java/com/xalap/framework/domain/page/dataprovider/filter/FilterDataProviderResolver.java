/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.domain.page.dataprovider.filter;

import com.xalap.framework.domain.page.dataprovider.PageableDataProvider;

import java.io.Serializable;

/**
 * @author Usov Andrey
 * @since 2020-04-10
 */
@FunctionalInterface
public interface FilterDataProviderResolver<T, F> extends Serializable {

    PageableDataProvider<T> resolve(F filter);

}
