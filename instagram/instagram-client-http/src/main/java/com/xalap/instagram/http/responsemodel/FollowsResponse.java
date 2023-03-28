
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
        "follows",
        "status"
})
public class FollowsResponse implements QueryIterator<FollowNode> {

    @JsonProperty("follows")
    private Follow follows;
    @JsonProperty("status")
    private String status;

    @JsonProperty("follows")
    public Follow getFollows() {
        return follows;
    }

    @JsonProperty("follows")
    public void setFollows(Follow follows) {
        this.follows = follows;
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
    public boolean hasNext() {
        return follows.getPageInfo().getHasNextPage();
    }

    @Override
    public List<FollowNode> next() {
        return follows.getNodes();
    }

    @Override
    public String getLastObjectId() {
        return follows.getPageInfo().getEndCursor();
    }
}
