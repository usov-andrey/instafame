
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
        "is_enabled"
})
public class P_______________ {

    @JsonProperty("is_enabled")
    private String isEnabled;

    @JsonProperty("is_enabled")
    public String getIsEnabled() {
        return isEnabled;
    }

    @JsonProperty("is_enabled")
    public void setIsEnabled(String isEnabled) {
        this.isEnabled = isEnabled;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
