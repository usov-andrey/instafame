
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
        "discover_desktop"
})
public class P____ {

    @JsonProperty("discover_desktop")
    private String discoverDesktop;

    @JsonProperty("discover_desktop")
    public String getDiscoverDesktop() {
        return discoverDesktop;
    }

    @JsonProperty("discover_desktop")
    public void setDiscoverDesktop(String discoverDesktop) {
        this.discoverDesktop = discoverDesktop;
    }

}
