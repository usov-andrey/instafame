
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider.vkserfing.getorder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "spent",
        "per_user"
})
public class Money {

    @JsonProperty("spent")
    private String spent;
    @JsonProperty("per_user")
    private String perUser;

    @JsonProperty("spent")
    public String getSpent() {
        return spent;
    }

    @JsonProperty("spent")
    public void setSpent(String spent) {
        this.spent = spent;
    }

    @JsonProperty("per_user")
    public String getPerUser() {
        return perUser;
    }

    @JsonProperty("per_user")
    public void setPerUser(String perUser) {
        this.perUser = perUser;
    }

}
