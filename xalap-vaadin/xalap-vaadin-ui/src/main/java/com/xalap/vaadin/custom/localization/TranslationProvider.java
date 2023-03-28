/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom.localization;

import com.vaadin.flow.i18n.I18NProvider;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

/**
 * Загрузка локализованных сообщений
 *
 * @author Usov Andrey
 * @since 22.03.2022
 */
@Component
public class TranslationProvider implements I18NProvider {

    private final MessageSource messageSource;

    public TranslationProvider(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public List<Locale> getProvidedLocales() {
        return List.of(Locale.ENGLISH, new Locale("th"), new Locale("ru"));
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        try {
            return messageSource.getMessage(key, params, locale);
        } catch (NoSuchMessageException e) {
            return messageSource.getMessage(key, params, Locale.ENGLISH);
        }
    }
}
