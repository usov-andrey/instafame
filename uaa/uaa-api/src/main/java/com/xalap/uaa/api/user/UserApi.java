/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.api.user;

import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.FilterPageableService;
import com.xalap.framework.domain.page.PageableEntityService;

/**
 * Интерфейс по работе с пользователями
 *
 * @author Usov Andrey
 * @since 14.05.2021
 */
public interface UserApi extends PageableEntityService<UserDto, Long>,
        FilterPageableService<UserDto, SearchFilter> {
}
