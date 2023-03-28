
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.sendgrid.sendmail;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "name"
})
public class SendGridEMailAddress {

    @JsonProperty("email")
    private String email;
    @JsonProperty("name")
    private String name;

    public SendGridEMailAddress() {
    }

    public SendGridEMailAddress(String email) {
        this.email = email;
    }

    public SendGridEMailAddress(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SendGridEMailAddress{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
