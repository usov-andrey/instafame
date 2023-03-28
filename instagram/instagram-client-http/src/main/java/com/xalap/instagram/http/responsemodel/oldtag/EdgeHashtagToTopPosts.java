
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.oldtag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "edges"
})
public class EdgeHashtagToTopPosts {

    @JsonProperty("edges")
    private List<Edge__> edges = null;

    @JsonProperty("edges")
    public List<Edge__> getEdges() {
        return edges;
    }

    @JsonProperty("edges")
    public void setEdges(List<Edge__> edges) {
        this.edges = edges;
    }

}
