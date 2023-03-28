
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
        "discover_media"
})
public class P__ {

    @JsonProperty("discover_media")
    private String discoverMedia;

    @JsonProperty("discover_media")
    public String getDiscoverMedia() {
        return discoverMedia;
    }

    @JsonProperty("discover_media")
    public void setDiscoverMedia(String discoverMedia) {
        this.discoverMedia = discoverMedia;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
