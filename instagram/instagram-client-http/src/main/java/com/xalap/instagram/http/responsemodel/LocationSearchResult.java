
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
        "location",
        "logging_page_id"
})
public class LocationSearchResult {

    @JsonProperty("location")
    private Location location;
    @JsonProperty("logging_page_id")
    private String loggingPageId;

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("logging_page_id")
    public String getLoggingPageId() {
        return loggingPageId;
    }

    @JsonProperty("logging_page_id")
    public void setLoggingPageId(String loggingPageId) {
        this.loggingPageId = loggingPageId;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
