
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
        "client_nav"
})
public class P________ {

    @JsonProperty("client_nav")
    private String clientNav;

    @JsonProperty("client_nav")
    public String getClientNav() {
        return clientNav;
    }

    @JsonProperty("client_nav")
    public void setClientNav(String clientNav) {
        this.clientNav = clientNav;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
