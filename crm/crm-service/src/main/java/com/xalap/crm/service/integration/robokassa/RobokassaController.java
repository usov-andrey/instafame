/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * https://forms.tildacdn.com/payment/robokassa/
 *
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Controller
@RequestMapping(RobokassaController.NAME)
public class RobokassaController {

    public static final String NAME = "robokassa";
    public static final String ORDER_ID = "InvId";

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    private RobokassaService service;

    @RequestMapping(method = RequestMethod.POST, value = "{shopId}")
    public
    @ResponseBody
    String post(@PathVariable String shopId, @RequestParam(value = ORDER_ID) String orderId) {
        service.processPayment(shopId, request);
        return "OK" + HtmlUtils.htmlEscape(orderId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{shopId}")
    public
    @ResponseBody
    String get(@PathVariable String shopId, @RequestParam(value = ORDER_ID) String orderId) {
        return post(shopId, HtmlUtils.htmlEscape(orderId));
    }
}
