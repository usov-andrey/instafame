/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.utils;

import com.xalap.framework.exception.IORuntimeException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;

import java.io.IOException;

/**
 * Разные функции по быстрой работе c http
 * По-умолчанию установлены другие, более правильные ограничения и параметры,
 * нежели по-умолчанию в fluent api
 *
 * @author Usov Andrey
 * @since 20.11.2020
 */
public class HttpClientUtils {

    private static final int DEFAULT_TIMEOUT_SECONDS = 5;

    public static String postJson(String payload, String url) {
        try {
            return post(url, ContentType.APPLICATION_JSON, payload);
        } catch (IOException e) {
            throw new IORuntimeException("Error on post json to url:" + url + " with body:" + payload, e);
        }
    }

    /**
     * @return Выполнить POST запрос с телом payload
     */
    public static String post(String url, ContentType contentType, String payload) throws IOException {
        return execDefaultWithPayload(Request.Post(url), contentType, payload);
    }

    /**
     * @return Выполнить GET запрос
     */
    public static String get(String url) throws IOException {
        return execDefaultStringResponse(Request.Get(url));
    }

    public static Response exec(Request request, int timeoutSeconds) {
        try {
            return request.socketTimeout(timeoutSeconds * 1000).connectTimeout(timeoutSeconds * 1000).execute();
        } catch (IOException e) {
            throw new IORuntimeException("Error on exec request:" + request, e);
        }
    }

    public static Request chrome(Request request) {
        return request.userAgent(UserAgentUtils.chrome());
    }

    /**
     * Выполняем запрос с параметрами по-умолчанию
     */
    public static Response execDefault(Request request) {
        return exec(chrome(request), DEFAULT_TIMEOUT_SECONDS);
    }

    public static String execDefaultWithPayload(Request request, ContentType contentType, String payload) throws IOException {
        return execDefaultStringResponse(request.bodyString(payload, contentType));
    }

    public static String execDefaultStringResponse(Request request) {
        Response response = execDefault(request);
        return stringResponse(response);
    }

    public static String stringResponse(Response response) {
        try {
            return response.returnContent().asString(WebHelper.CHARSET_UTF8);
        } catch (IOException e) {
            throw new IORuntimeException("Error on process response:" + response, e);
        }
    }

}
