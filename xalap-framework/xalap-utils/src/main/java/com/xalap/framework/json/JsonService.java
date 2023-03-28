/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.framework.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Service;

import java.util.TimeZone;

/**
 * @deprecated
 * Нужно настраивать ObjectMapper через spring boot и использовать его
 * В каждом случае сериализации/десериализации нужно корректно обрабатывать исключение
 *
 * @author Usov Andrey
 * @since 2020-08-27
 */
@Service
@Deprecated
public class JsonService {

    private final ObjectMapper jsonMapper;

    public JsonService() {
        this.jsonMapper = new ObjectMapper();
        this.jsonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        this.jsonMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        this.jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.jsonMapper.setTimeZone(TimeZone.getDefault());
    }

    /**
     * @return Обьект класс objectClass из строкового представления json
     */
    public <T> T getJson(String value, Class<T> objectClass) {
        try {
            return this.jsonMapper.readValue(value, objectClass);
        } catch (JsonProcessingException e) {
            throw new JsonConvertingException("Error on parse json:" + value, e);
        }
    }

    public String getString(Object object) {
        try {
            return jsonMapper.writer().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new JsonConvertingException("Error to convert object to json string:" + object, e);
        }
    }
}
