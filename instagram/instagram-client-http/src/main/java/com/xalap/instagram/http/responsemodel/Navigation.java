
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
        "forward",
        "replay"
})
public class Navigation {

    @JsonProperty("forward")
    private String forward;
    @JsonProperty("replay")
    private String replay;

    @JsonProperty("forward")
    public String getForward() {
        return forward;
    }

    @JsonProperty("forward")
    public void setForward(String forward) {
        this.forward = forward;
    }

    @JsonProperty("replay")
    public String getReplay() {
        return replay;
    }

    @JsonProperty("replay")
    public void setReplay(String replay) {
        this.replay = replay;
    }

}
