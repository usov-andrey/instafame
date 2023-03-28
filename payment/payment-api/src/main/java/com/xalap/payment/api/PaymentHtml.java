/*
 *  All rights reserved by Xalap.
 *  Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.payment.api;

/**
 *
 * @author Usov Andrey
 * @since 2020-04-16
 */
public class PaymentHtml {

    private final String formHtml;

    public PaymentHtml(String formHtml) {
        this.formHtml = formHtml;
    }

    /**
     * html код формы для перенаправления на платежную систему
     */
    public String getFormHtml() {
        return formHtml;
    }

    /**
     * @return Javascript для submit формы
     */
    public String getJavaScript() {
        return "document.forms['payment-form'].submit();";
    }

    @Override
    public String toString() {
        return formHtml;
    }
}
