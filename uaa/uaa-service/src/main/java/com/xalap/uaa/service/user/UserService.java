/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.user;

import com.xalap.framework.data.CrudService;
import com.xalap.framework.data.NotFoundException;
import com.xalap.framework.logging.HasLog;
import com.xalap.framework.utils.RandomUtils;
import com.xalap.uaa.api.exception.EmailAlreadyUsedException;
import com.xalap.uaa.api.exception.InvalidPasswordException;
import com.xalap.uaa.api.exception.TooManyResetPasswordRequestsException;
import com.xalap.uaa.service.authority.Authority;
import com.xalap.uaa.service.authority.AuthorityRepository;
import com.xalap.uaa.service.utils.SecurityUtils;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static com.xalap.uaa.api.Role.ROLE_USER;

/**
 * Service class for managing users.
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
@Service
public class UserService extends CrudService<User, UserRepository, Long> implements HasLog {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    private final CacheManager cacheManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository, CacheManager cacheManager) {
        super();
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public User findByLogin(String login) throws NotFoundException {
        return repository().findOneByLogin(login).orElseThrow(() -> new NotFoundException("Not found user by login " + login));
    }

    public Optional<User> activateRegistration(String key) {
        log().debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    save(user);
                    log().debug("Activated user: {}", user);
                    return user;
                });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
        log().debug("Reset user password for reset key {}", key);
        return userRepository.findOneByResetKey(key)
                .filter(user -> user.getResetDate().isAfter(Instant.now().minusSeconds(86400)))
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    user.setResetKey(null);
                    user.setResetDate(null);
                    save(user);
                    log().debug("Password changed for user: {}", user);
                    return user;
                });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::getActivated)
                .map(user -> {
                    Instant now = Instant.now();
                    //Повторить попытку отправки можно только через минуту
                    if (user.getResetDate() != null &&
                            user.getResetDate().plus(1, ChronoUnit.MINUTES).isAfter(now)) {
                        //Повторная попытка сбросить пароль в пределах одной минуты
                        throw new TooManyResetPasswordRequestsException(mail);
                    }
                    user.setResetKey(RandomUtils.generateRandomAlphanumericString());
                    user.setResetDate(now);
                    save(user);
                    return user;
                });
    }

    public User registerUser(String name, String email, String password) throws EmailAlreadyUsedException {
        String login = email.toLowerCase();
        userRepository.findOneByEmailIgnoreCase(email).ifPresent(existingUser -> {
            boolean removed = removeNonActivatedUser(existingUser);
            if (!removed) {
                throw new EmailAlreadyUsedException(email);
            }
        });
        return createUser(name, login, email, password, ROLE_USER);
    }

    public User createUser(String name, String login, String email, String password, String authority) {
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login.toLowerCase());
        newUser.setEmail(email);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setName(name);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtils.generateRandomAlphanumericString());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(authority).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        save(newUser);
        log().debug("Created Information for User: {}", newUser);
        return newUser;
    }

    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.getActivated()) {
            return false;
        }
        delete(existingUser);
        return true;
    }

    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            delete(user);
            log().debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils.getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    save(user);
                    log().debug("Changed password for User: {}", user);
                });
    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        userRepository
                .findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant.now().minus(3, ChronoUnit.DAYS))
                .forEach(user -> {
                    log().debug("Deleting not activated user {}", user.getLogin());
                    delete(user);
                });
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE)).evict(user.getLogin());
        if (user.getEmail() != null) {
            Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE)).evict(user.getEmail());
        }
    }

    @Override
    protected void onChange(User user) {
        clearUserCaches(user);
    }
}