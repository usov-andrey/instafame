
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "activityFeed",
        "followRequests",
        "timestamp"
})
public class ActivityFeedResponse {

    @JsonProperty("activityFeed")
    private ActivityFeed activityFeed;
    @JsonProperty("followRequests")
    private List<Object> followRequests = null;
    @JsonProperty("timestamp")
    private Double timestamp;

    @JsonProperty("activityFeed")
    public ActivityFeed getActivityFeed() {
        return activityFeed;
    }

    @JsonProperty("activityFeed")
    public void setActivityFeed(ActivityFeed activityFeed) {
        this.activityFeed = activityFeed;
    }

    @JsonProperty("followRequests")
    public List<Object> getFollowRequests() {
        return followRequests;
    }

    @JsonProperty("followRequests")
    public void setFollowRequests(List<Object> followRequests) {
        this.followRequests = followRequests;
    }

    @JsonProperty("timestamp")
    public Double getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
