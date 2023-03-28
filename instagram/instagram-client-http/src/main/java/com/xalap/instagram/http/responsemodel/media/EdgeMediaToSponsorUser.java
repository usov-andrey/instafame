
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "edges"
})
public class EdgeMediaToSponsorUser {

    @JsonProperty("edges")
    private List<Object> edges = null;

    @JsonProperty("edges")
    public List<Object> getEdges() {
        return edges;
    }

    @JsonProperty("edges")
    public void setEdges(List<Object> edges) {
        this.edges = edges;
    }

}
