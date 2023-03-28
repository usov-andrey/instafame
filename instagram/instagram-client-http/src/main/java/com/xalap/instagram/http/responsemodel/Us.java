
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
        "g",
        "p"
})
public class Us {

    @JsonProperty("g")
    private String g;
    @JsonProperty("p")
    private P______ p;

    @JsonProperty("g")
    public String getG() {
        return g;
    }

    @JsonProperty("g")
    public void setG(String g) {
        this.g = g;
    }

    @JsonProperty("p")
    public P______ getP() {
        return p;
    }

    @JsonProperty("p")
    public void setP(P______ p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
