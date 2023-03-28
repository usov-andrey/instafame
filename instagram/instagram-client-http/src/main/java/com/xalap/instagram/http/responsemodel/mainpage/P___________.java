
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.mainpage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "chaining",
        "dismiss"
})
public class P___________ {

    @JsonProperty("chaining")
    private String chaining;
    @JsonProperty("dismiss")
    private String dismiss;

    @JsonProperty("chaining")
    public String getChaining() {
        return chaining;
    }

    @JsonProperty("chaining")
    public void setChaining(String chaining) {
        this.chaining = chaining;
    }

    @JsonProperty("dismiss")
    public String getDismiss() {
        return dismiss;
    }

    @JsonProperty("dismiss")
    public void setDismiss(String dismiss) {
        this.dismiss = dismiss;
    }

}
