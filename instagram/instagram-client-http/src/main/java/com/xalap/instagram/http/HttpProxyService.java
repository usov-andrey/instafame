/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http;

import com.xalap.framework.exception.ServiceTemporaryException;
import com.xalap.framework.utils.HttpClientUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Выполнение http запросов через список прокси
 *
 * Только с каких-то доверенных api выполняются запросы к инстаграм без авторизации
 *
 * @author Usov Andrey
 * @since 2020-08-27
 */
@Service
public class HttpProxyService {

    private final List<HttpHost> proxies = new ArrayList<>();

    public HttpProxyService() {
        proxies.add(new HttpHost("198.143.183.164", 3128, "http"));
    }

    public <T> T execWithProxy(String url, Function<String, T> responseResultFunction) throws ServiceTemporaryException {
        ServiceTemporaryException exception = null;
        for (HttpHost proxy : proxies) {
            try {
                String response = execGetWithProxy(url, proxy);
                return responseResultFunction.apply(response);
            } catch (ServiceTemporaryException e) {
                exception = e;
            }
        }
        if (exception == null) {
            throw new IllegalStateException("No proxies:" + proxies);
        }
        throw exception;
    }

    private String execGetWithProxy(String url, HttpHost proxy) throws ServiceTemporaryException {
        try {
            /*
            HttpGet request = new HttpGet(url);
            try (CloseableHttpClient closeableHttpClient = HttpHelper.createClient()) {
                try (CloseableHttpResponse response = closeableHttpClient.execute(request)) {
                    System.out.println("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    return EntityUtils.toString(response.getEntity());
                }
            }*/

            url = "https://app.zenscrape.com/api/v1/get?render=true&url=" + url;
            Request request = Request.Get(url)
                    .addHeader("apikey", "468fade0-fa42-11ea-a304-61fd77e54bef");
            return HttpClientUtils.execDefaultStringResponse(request);
            /**
             HttpClientBuilder httpClientBuilder = HttpService.clientBuilder();
             httpClientBuilder.setProxy(proxy);

             HttpGet get = Http.get(httpClientBuilder.build(), url);
             return get.responseBody();**/
        } catch (Exception e) {
            throw new ServiceTemporaryException("Error on request:" + url + " with proxy:" + proxy, e);
        }
    }
}
