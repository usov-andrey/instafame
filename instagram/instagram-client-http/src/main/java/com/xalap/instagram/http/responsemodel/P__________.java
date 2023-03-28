
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "show_phone_or_email_text",
        "try_email_and_phone_login"
})
public class P__________ {

    @JsonProperty("show_phone_or_email_text")
    private String showPhoneOrEmailText;
    @JsonProperty("try_email_and_phone_login")
    private String tryEmailAndPhoneLogin;

    @JsonProperty("show_phone_or_email_text")
    public String getShowPhoneOrEmailText() {
        return showPhoneOrEmailText;
    }

    @JsonProperty("show_phone_or_email_text")
    public void setShowPhoneOrEmailText(String showPhoneOrEmailText) {
        this.showPhoneOrEmailText = showPhoneOrEmailText;
    }

    @JsonProperty("try_email_and_phone_login")
    public String getTryEmailAndPhoneLogin() {
        return tryEmailAndPhoneLogin;
    }

    @JsonProperty("try_email_and_phone_login")
    public void setTryEmailAndPhoneLogin(String tryEmailAndPhoneLogin) {
        this.tryEmailAndPhoneLogin = tryEmailAndPhoneLogin;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
