/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.tilda;

import com.xalap.framework.web.request.SerializableHttpRequest;

/**
 * Запрос от tilda
 * @author Усов Андрей
 * @since 12/06/2019
 */
public class TildaSubmitFormRequest extends SerializableHttpRequest {

    public String getTransactionId() {
        return getParameter("tranid");
    }

    public String getFormId() {
        return getParameter("formid");
    }

}
