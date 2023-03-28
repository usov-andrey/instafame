
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
        "activity_feed",
        "edge_follow_requests"
})
public class User {

    @JsonProperty("activity_feed")
    private ActivityFeed activityFeed;
    @JsonProperty("edge_follow_requests")
    private EdgeFollowRequests edgeFollowRequests;

    @JsonProperty("activity_feed")
    public ActivityFeed getActivityFeed() {
        return activityFeed;
    }

    @JsonProperty("activity_feed")
    public void setActivityFeed(ActivityFeed activityFeed) {
        this.activityFeed = activityFeed;
    }

    @JsonProperty("edge_follow_requests")
    public EdgeFollowRequests getEdgeFollowRequests() {
        return edgeFollowRequests;
    }

    @JsonProperty("edge_follow_requests")
    public void setEdgeFollowRequests(EdgeFollowRequests edgeFollowRequests) {
        this.edgeFollowRequests = edgeFollowRequests;
    }

}
