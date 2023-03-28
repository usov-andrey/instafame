/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.url;

import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Если в настройках задан какой-то web контекст, то он добавляется к url
 * Нужен для того, чтобы можно было корректно работать в ситуации, когда
 * снаружи идет обращение к http://domain/ а java приложение работает
 * как http://localhost:8080/test/
 * <p>
 * Для этого в настройках указываем контекст / и все редиректы и ссылки работают
 * как нужно
 *
 * @author Usov Andrey
 * @since 2020-04-03
 */
@Component
public class UrlManager {

    private final String applicationUrl;
    private final String webContextPath;

    public UrlManager(@Value("${web-context-path:/}") String webContextPath,
                      @Value("${application-url:}") String applicationUrl) {
        this.webContextPath = WebHelper.addTrailingSlash(webContextPath);
        this.applicationUrl = applicationUrl;
    }

    public String url(String source) {
        return WebHelper.getURL(webContextPath, source);
    }

    public boolean isRootContext() {
        return StringHelper.isEmpty(webContextPath) || webContextPath.equals("/");
    }

    public RedirectView redirectView(String url) {
        return new RedirectView(url, isRootContext());
    }

    public String getApplicationUrlWithMapping(String mapping) {
        return WebHelper.getURL(applicationUrl, mapping);
    }
}
