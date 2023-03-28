/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.user;

import com.xalap.framework.data.dto.DtoCrudService;
import com.xalap.framework.domain.filter.SearchFilter;
import com.xalap.framework.domain.page.request.PageRequest;
import com.xalap.uaa.api.user.UserApi;
import com.xalap.uaa.api.user.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Usov Andrey
 * @since 14.05.2021
 */
@Service
public class UserApiService extends DtoCrudService<UserDto, User, UserService, Long> implements UserApi {

    @Override
    public List<UserDto> findPage(PageRequest pageable, SearchFilter filter) {
        String search = filter.getSearch();
        return toDto(service.repository().findByNameContainingOrEmailContainingOrLoginContaining(service.pageable(pageable),
                search, search, search));
    }

    @Override
    public long count(SearchFilter filter) {
        String search = filter.getSearch();
        return service.repository().countByNameContainingOrEmailContainingOrLoginContaining(search, search, search);
    }
}
