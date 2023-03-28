
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
        "take"
})
public class P____________ {

    @JsonProperty("take")
    private String take;

    @JsonProperty("take")
    public String getTake() {
        return take;
    }

    @JsonProperty("take")
    public void setTake(String take) {
        this.take = take;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
