/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.tilda;

import com.xalap.framework.json.JsonService;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.web.request.HttpForm;
import com.xalap.framework.web.request.SerializableHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
public class TildaForm extends HttpForm {

    public static final String PAYMENT = "payment";
    private final JsonService converterService;

    public TildaForm(SerializableHttpRequest request, JsonService converterService) {
        super(request);
        this.converterService = converterService;
    }

    public TildaPayment getPayment() {
        String paymentJson = getPaymentString();
        return converterService.getJson(paymentJson, TildaPayment.class);
    }

    public String getPaymentString() {
        return request.getParameter(PAYMENT);
    }

    @Override
    public String getCookies() {
        String cookies = request.getParameter("COOKIES");
        if (StringHelper.isNotEmpty(cookies)) {
            //Иногда может придти пробел вначале, убираем
            cookies = cookies.trim();
        }
        return cookies;
    }

    public Map<String, Double> products() {
        //Продукты приходит от тильды в таком виде
        //["1000 подписчиков=3450"]
        Map<String, Double> result = new HashMap<>();
        TildaPayment payment = getPayment();
        for (String s : payment.getProducts()) {
            String[] values = s.split("=");
            double price = Double.parseDouble(values[1]);
            if (price > 0) {
                result.put(values[0], price);
            }
        }
        return result;
    }

    public String getNotNull(String name) {
        String value = get(name);
        if (value == null) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Not found parameter " + name + " in tildaForm:" + this);
        }
        return value;
    }

}
