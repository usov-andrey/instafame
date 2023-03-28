/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.account;

import com.xalap.uaa.api.exception.NoAccessException;
import com.xalap.uaa.service.authority.AuthorityService;
import com.xalap.uaa.service.config.SecurityConfiguration;
import com.xalap.uaa.service.user.User;
import com.xalap.uaa.service.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

/**
 * Функции по работе с аккаунтом пользователя
 *
 * @author Usov Andrey
 * @since 2020-04-14
 */
@Service
@AllArgsConstructor
public class AccountService {

    private final AuthorityService authorityService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    /**
     * Войти под user
     */
    void loginAndSaveInResponseRememberMeToken(HttpServletRequest request, HttpServletResponse response, User user) {
        List<GrantedAuthority> grantedAuthorities = authorityService.getGrantedAuthorities(user.getAuthorities());
        RememberMeAuthenticationToken authenticationToken = new RememberMeAuthenticationToken(
                SecurityConfiguration.REM_ME,
                user.getLogin(), grantedAuthorities);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //Создаем новый экземпляр, чтобы в общем иметь возможность выставить alwaysRemember в false
        TokenBasedRememberMeServices rememberMeServices =
                new TokenBasedRememberMeServices(SecurityConfiguration.REM_ME, userDetailsService);
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.loginSuccess(request, response, authenticate);
    }

    private Optional<String> getCurrentUserLogin() {
        return SecurityUtils.getCurrentUserLogin();
    }

    /**
     * Возвращает логин авторизованного пользователя, или выбрасывает исключение, ели пользователь не авторизован
     */
    String getAuthenticatedUserLogin() throws NoAccessException {
        Optional<String> currentUserLogin = getCurrentUserLogin();
        if (SecurityUtils.isAuthenticated() && currentUserLogin.isPresent()) {
            return currentUserLogin.get();
        }
        throw new NoAccessException("User not authenticated");
    }
}