/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.uaa.service.account;

import com.xalap.framework.exception.ClientErrorException;
import com.xalap.framework.utils.WebHelper;
import com.xalap.framework.web.url.UrlManager;
import com.xalap.uaa.api.account.AccountApi;
import com.xalap.uaa.api.account.UaaMailApi;
import com.xalap.uaa.api.exception.InvalidPasswordException;
import com.xalap.uaa.api.web.UaaHtmlPage;
import com.xalap.uaa.service.config.SecurityConfiguration;
import com.xalap.uaa.service.config.SecurityProperties;
import com.xalap.uaa.service.user.User;
import com.xalap.uaa.service.user.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Публичное api для внешних операций с пользователем
 *
 * @author Usov Andrey
 * @since 2020-04-06
 */
@RestController
@RequestMapping(AccountController.NAME)
@AllArgsConstructor
public class AccountController implements AccountApi {

    public static final String NAME = "account";
    private static final String MAPPING_REDIRECT_BY_TOKEN = "/redirectByToken";

    private final Logger log = LoggerFactory.getLogger(AccountController.class);

    private final UserService userService;
    private final UaaMailApi mailService;
    private final PasswordGenerator passwordGenerator;
    private final SecurityConfiguration securityConfiguration;
    private final UrlManager urlManager;
    private final AccountService accountService;
    private final SecurityProperties securityProperties;

    /**
     * Получение информации о пользователе
     */
    @Override
    public User getUser(String login) {
        return userService.findByLogin(login);
    }

    /**
     * Логин текущего пользователя
     */
    @Override
    public String getAuthenticatedUserLogin() {
        return accountService.getAuthenticatedUserLogin();
    }

    /**
     * {@code POST  /register} : register the user.
     */
    @PostMapping("/registration")
    public RedirectView registerAccountAndRedirect(@RequestParam(value = "username") String name, @RequestParam String email) {
        registerAccount(name, email);
        return urlManager.redirectView(WebHelper.URL_SEPARATOR + UaaHtmlPage.SUCCESS_REGISTRATION.getHtmlFileName());
    }

    @Override
    public void registerAccount(String name, String email) {
        String password = passwordGenerator.generatePassword();
        User user = userService.registerUser(name, email, password);
        mailService.sendActivationEmail(user, password, generateUrlForActivateAccount(user));
    }

    @GetMapping(MAPPING_REDIRECT_BY_TOKEN)
    public RedirectView activateAccount(@RequestParam(value = "key") String key,
                                        HttpServletRequest request, HttpServletResponse response) {
        Optional<User> userOptional = userService.activateRegistration(key);
        if (userOptional.isEmpty()) {
            return urlManager.redirectView(securityConfiguration.loginFailureUrl());
        }
        accountService.loginAndSaveInResponseRememberMeToken(request, response, userOptional.get());
        return urlManager.redirectView(securityProperties.getLogin().getSuccessUrl());
    }

    /**
     * Url по которому можно войти с помощью ключа активации
     */
    private String generateUrlForActivateAccount(User user) {
        return urlManager.getApplicationUrlWithMapping(NAME + MAPPING_REDIRECT_BY_TOKEN + "?key=" +
                user.getActivationKey());
    }

    /**
     * {@code POST   /account/reset-password/init} : Send an email to reset the password of the user.
     *
     * @param mail the mail of the user.
     */
    @PostMapping(path = "/reset-password/init")
    @Override
    public void requestPasswordReset(@RequestBody String mail) {
        Optional<User> user = userService.requestPasswordReset(mail);
        if (user.isPresent()) {
            mailService.sendPasswordResetMail(user.get());
        } else {
            // Pretend the request has been successful to prevent checking which emails really exist
            // but log that an invalid attempt has been made
            log.warn("Password reset requested for non existing mail '{}'", mail);
        }
    }

    /**
     * {@code POST   /account/reset-password/finish} : Finish to reset the password of the user.
     *
     * @param keyAndPassword the generated key and the new password.
     * @throws InvalidPasswordException {@code 400 (Bad Request)} if the password is incorrect.
     * @throws RuntimeException         {@code 500 (Internal Server Error)} if the password could not be reset.
     */
    @PostMapping(path = "/reset-password/finish")
    public void finishPasswordReset(@RequestBody KeyAndPasswordVM keyAndPassword) {
        if (!checkPasswordLength(keyAndPassword.getNewPassword())) {
            throw new InvalidPasswordException();
        }
        Optional<User> user =
                userService.completePasswordReset(keyAndPassword.getNewPassword(), keyAndPassword.getKey());

        if (user.isEmpty()) {
            throw new ClientErrorException("Не найдено пользователя для ключа и пароля: " + keyAndPassword);
        }
    }

    private static boolean checkPasswordLength(String password) {
        return !StringUtils.isEmpty(password) &&
                password.length() >= KeyAndPasswordVM.PASSWORD_MIN_LENGTH &&
                password.length() <= KeyAndPasswordVM.PASSWORD_MAX_LENGTH;
    }
}
