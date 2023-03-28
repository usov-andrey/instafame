
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.feed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.responsemodel.Story;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "node"
})
public class Edge {

    @JsonProperty("node")
    private Story node;

    @JsonProperty("node")
    public Story getNode() {
        return node;
    }

    @JsonProperty("node")
    public void setNode(Story node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
