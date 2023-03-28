/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.request;

import com.xalap.framework.utils.WebHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 12/06/2019
 */
public class SerializableHttpRequest implements Serializable {

    private Map<String, String> parametersMap;
    private Map<String, String> headersMap;
    private String cookies;

    public SerializableHttpRequest setRequest(HttpServletRequest request) {
        parametersMap = WebHelper.getRequestParameterMap(request);
        headersMap = WebHelper.getRequestHeaderParameters(request);
        cookies = WebHelper.getCookies(request.getCookies());
        return this;
    }

    public Map<String, String> getParametersMap() {
        return parametersMap;
    }

    public void setParametersMap(Map<String, String> parametersMap) {
        this.parametersMap = parametersMap;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getParameter(String paramName) {
        return parametersMap.get(paramName);
    }

    public String referrer() {
        return headersMap.get("referer");
    }

    @Override
    public String toString() {
        return "SerializableHttpRequest{" +
                "parametersMap=" + parametersMap +
                ", headersMap=" + headersMap +
                ", cookies='" + cookies + "'}";
    }
}
