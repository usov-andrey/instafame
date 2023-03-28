/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.tilda;

import com.xalap.framework.notification.NotificationService;
import com.xalap.framework.utils.WebHelper;
import com.xalap.framework.web.request.HttpRequestProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Controller
@RequestMapping(TildaController.NAME)
public class TildaController {

    private static final Logger log = LoggerFactory.getLogger(TildaController.class);

    public static final String NAME = "tilda";
    public static final String FORM_ID = "formid";

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected NotificationService notificationService;
    @Autowired
    private HttpRequestProcessor httpRequestProcessor;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public
    @ResponseBody
    String formSubmit(@RequestParam(value = FORM_ID, required = false) String formId) {
        if (formId != null) {
            httpRequestProcessor.createAndProcess(TildaSubmitFormRequest.class, request);
        } else {
            log.debug("Not found " + FORM_ID + " in request:" + WebHelper.getRequestInfoForLogging(request));
        }
        return "1";
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public
    @ResponseBody
    String formGet(@RequestParam(value = FORM_ID, required = false) String formId) {
        return formSubmit(formId);
    }

}
