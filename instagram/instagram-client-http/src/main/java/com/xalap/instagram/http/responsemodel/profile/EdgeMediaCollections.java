
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "count",
        "page_info",
        "edges"
})
public class EdgeMediaCollections {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("page_info")
    private PageInfo__ pageInfo;
    @JsonProperty("edges")
    private List<Object> edges = null;

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("page_info")
    public PageInfo__ getPageInfo() {
        return pageInfo;
    }

    @JsonProperty("page_info")
    public void setPageInfo(PageInfo__ pageInfo) {
        this.pageInfo = pageInfo;
    }

    @JsonProperty("edges")
    public List<Object> getEdges() {
        return edges;
    }

    @JsonProperty("edges")
    public void setEdges(List<Object> edges) {
        this.edges = edges;
    }

}
