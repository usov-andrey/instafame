
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.follow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "edge_follow"
})
public class User {

    @JsonProperty("edge_follow")
    private EdgeFollow edgeFollow;

    @JsonProperty("edge_follow")
    public EdgeFollow getEdgeFollow() {
        return edgeFollow;
    }

    @JsonProperty("edge_follow")
    public void setEdgeFollow(EdgeFollow edgeFollow) {
        this.edgeFollow = edgeFollow;
    }

}
