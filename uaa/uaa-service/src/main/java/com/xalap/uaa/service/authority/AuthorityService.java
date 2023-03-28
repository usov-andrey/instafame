/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.authority;

import com.xalap.framework.data.CrudService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Service
public class AuthorityService extends CrudService<Authority, AuthorityRepository, String> {

    public AuthorityService() {
        super();
    }

    /**
     * Конвертируем из одной модели в другую
     */
    public List<GrantedAuthority> getGrantedAuthorities(Set<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
    }

}
