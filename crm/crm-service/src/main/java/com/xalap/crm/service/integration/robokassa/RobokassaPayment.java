/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.robokassa;

import com.xalap.framework.utils.WebHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Усов Андрей
 * @since 17/01/2019
 */
public class RobokassaPayment {

    public static final String FEE = "Fee";
    public static final String E_MAIL = "EMail";
    public static final String SIGNATURE_VALUE = "SignatureValue";
    public static final String INV_ID = "InvId";
    public static final String IS_TEST = "IsTest";
    public static final String OUT_SUM = "OutSum";
    public static final String CURR_LABEL = "IncCurrLabel";
    public static final String PAYMENT_METHOD = "PaymentMethod";

    private final HttpServletRequest request;

    public RobokassaPayment(HttpServletRequest request) {
        this.request = request;
    }

    public Double customerPayAmount() {
        return Double.parseDouble(get(OUT_SUM));
    }

    public Double fee() {
        return Double.parseDouble(get(FEE));
    }

    public Double payMinusFee() {
        return customerPayAmount() - fee();
    }

    public String customerEmail() {
        return get(E_MAIL);
    }

    public boolean isTest() {
        return "1".equals(get(IS_TEST));
    }

    public String getSign() {
        return get(SIGNATURE_VALUE);
    }

    public String getInvoiceId() {
        return get(INV_ID);
    }

    public String get(String paramName) {
        return request.getParameter(paramName);
    }

    public String getPaymentMethod() {
        return get(PAYMENT_METHOD);
    }

    public String getCurrencyLabel() {
        return get(CURR_LABEL);
    }

    @Override
    public String toString() {
        return "RobokassaPayment{" +
                "request=" + WebHelper.getRequestInfoForLogging(request) +
                '}';
    }
}
