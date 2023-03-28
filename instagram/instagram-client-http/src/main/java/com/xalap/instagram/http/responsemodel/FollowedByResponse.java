
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
        "followed_by",
        "status"
})
public class FollowedByResponse implements QueryIterator<FollowNode> {

    @JsonProperty("followed_by")
    private Follow followedBy;
    @JsonProperty("status")
    private String status;

    @JsonProperty("followed_by")
    public Follow getFollowedBy() {
        return followedBy;
    }

    @JsonProperty("followed_by")
    public void setFollowedBy(Follow followedBy) {
        this.followedBy = followedBy;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this + "";
    }

    @Override
    public String getLastObjectId() {
        return followedBy.getPageInfo().getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return followedBy.getPageInfo().getHasNextPage();
    }

    @Override
    public List<FollowNode> next() {
        return followedBy.getNodes();
    }

}
