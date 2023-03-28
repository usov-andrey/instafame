/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.request;

import com.xalap.framework.utils.StringHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 01/06/2019
 */
public class HttpForm {

    protected static final Logger log = LoggerFactory.getLogger(HttpForm.class);
    protected SerializableHttpRequest request;

    public HttpForm(SerializableHttpRequest request) {
        this.request = request;
    }

    /**
     * @return возвращает пустую строку, если clientId не найден
     */
    public String getClientId() {
        Optional<String> ga = getCookie("_ga");
        if (ga.isPresent()) {
            return ga.get().replace("GA1.2.", "");
        } else {
            log.warn("Not found GA in request:" + request);
            return "";
        }
    }

    public String getCookies() {
        return request.getCookies();
    }

    private Optional<String> getCookie(String name) {
        String cookies = getCookies();
        if (cookies != null) {
            for (String s : cookies.split(";")) {
                String key = StringHelper.getStringBefore(s, "=").trim();
                if (key.equalsIgnoreCase(name)) {
                    return Optional.of(StringHelper.getStringAfter(s, "=").trim());
                }
            }
        }
        return Optional.empty();
    }

    public String get(String paramName) {
        return request.getParameter(paramName);
    }

    public boolean has(String paramName) {
        return get(paramName) != null;
    }

    @Override
    public String toString() {
        return "Form{" +
                "request=" + request +
                '}';
    }

    public String getReferrer() {
        return request.referrer();
    }
}
