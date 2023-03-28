
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "timestamp",
        "edge_web_activity_feed"
})
public class ActivityFeed {

    @JsonProperty("timestamp")
    private Double timestamp;
    @JsonProperty("edge_web_activity_feed")
    private EdgeWebActivityFeed edgeWebActivityFeed;

    @JsonProperty("timestamp")
    public Double getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("edge_web_activity_feed")
    public EdgeWebActivityFeed getEdgeWebActivityFeed() {
        return edgeWebActivityFeed;
    }

    @JsonProperty("edge_web_activity_feed")
    public void setEdgeWebActivityFeed(EdgeWebActivityFeed edgeWebActivityFeed) {
        this.edgeWebActivityFeed = edgeWebActivityFeed;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
