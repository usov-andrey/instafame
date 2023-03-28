/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.config;

import com.xalap.uaa.service.authority.Authority;
import com.xalap.uaa.service.authority.AuthorityRepository;
import com.xalap.uaa.service.user.User;
import com.xalap.uaa.service.user.UserRepository;
import com.xalap.uaa.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.xalap.uaa.api.Role.ROLE_ADMIN;
import static com.xalap.uaa.api.Role.ROLE_USER;

/**
 * Начальные тестовые данные
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        createAuthority(ROLE_ADMIN);
        createAuthority(ROLE_USER);

        createUsers();

        alreadySetup = true;
    }

    protected void createUsers() {
        createUserIfNotFound("admin@test.com", "Admin", "admin", "adminPassword", ROLE_ADMIN);
        createUserIfNotFound("test@test.com", "TestUser", "testUser", "testPassword", ROLE_USER);
    }

    private void createAuthority(String name) {
        if (authorityRepository.findOneByName(name).isEmpty()) {
            Authority authority = new Authority();
            authority.setName(name);
            authorityRepository.save(authority);
        }
    }

    protected void createUserIfNotFound(String email, String name, String login, String password, String authority) {
        User user = getOrCreateUser(email, name, login, password, authority);
        if (!user.getActivated()) {
            userService.activateRegistration(user.getActivationKey());
        }
    }

    private User getOrCreateUser(String email, String name, String login, String password, String authority) {
        Optional<User> oneByEmailIgnoreCase = userRepository.findOneByLogin(login);
        if (oneByEmailIgnoreCase.isEmpty()) {
            return userService.createUser(name, login, email, password, authority);
        }
        return oneByEmailIgnoreCase.get();
    }

}
