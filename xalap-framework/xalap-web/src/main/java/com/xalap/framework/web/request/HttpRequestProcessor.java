/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.web.request;

import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.ReflectHelper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Когда результат выполнения запроса не важен, то можно пропустить
 * http запрос через очередь и тогда этот запрос будет точно обработан без ошибок
 *
 * @author Усов Андрей
 * @since 12/06/2019
 */
@Service
@AllArgsConstructor
public class HttpRequestProcessor {

    private static final Logger log = LoggerFactory.getLogger(HttpRequestProcessor.class);

    private final Map<Class<? extends SerializableHttpRequest>, Consumer<? extends SerializableHttpRequest>> map = new LinkedHashMap<>();

    private final JsonService jsonService;

    public Set<Class<? extends SerializableHttpRequest>> getClasses() {
        return map.keySet();
    }

    public <T extends SerializableHttpRequest> void register(Class<T> requestClass, Consumer<T> consumer) {
        map.put(requestClass, consumer);
    }

    public <T extends SerializableHttpRequest> void process(T request) {
        Consumer<T> consumer = (Consumer<T>) map.get(request.getClass());
        if (consumer == null) {
            throw new IllegalStateException("Not found processor for request:" + request);
        }
        log.debug("Process request " + request.getClass() + " : " + jsonService.getString(request));
        consumer.accept(request);
    }

    public void createAndProcess(Class<? extends SerializableHttpRequest> requestClass, HttpServletRequest request) {
        SerializableHttpRequest httpRequest = ReflectHelper.newInstance(requestClass);
        httpRequest.setRequest(request);
        process(httpRequest);
    }
}
