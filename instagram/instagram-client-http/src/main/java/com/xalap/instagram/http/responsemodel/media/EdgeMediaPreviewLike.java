
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
        "count",
        "edges"
})
public class EdgeMediaPreviewLike {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("edges")
    private List<Edge__> edges = null;

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("edges")
    public List<Edge__> getEdges() {
        return edges;
    }

    @JsonProperty("edges")
    public void setEdges(List<Edge__> edges) {
        this.edges = edges;
    }

}
