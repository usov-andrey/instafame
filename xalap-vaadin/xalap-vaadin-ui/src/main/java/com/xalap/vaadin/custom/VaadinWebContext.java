/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.vaadin.custom;

import com.xalap.framework.utils.WebHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Обязательно контекст с vaadin приложением должен заканчиваться слешем, иначе
 * VaadinServlet будет делать редирект
 *
 * @author Usov Andrey
 * @since 2020-04-04
 */
@Component
public class VaadinWebContext {

    private final String webContextPath;

    public VaadinWebContext(@Value("${vaadin.web-context:/}") String webContextPath) {
        this.webContextPath = WebHelper.addTrailingSlash(webContextPath);
    }

    public String get() {
        return webContextPath;
    }
}
