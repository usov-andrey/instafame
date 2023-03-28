
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.model.Comment;
import com.xalap.instagram.http.responsemodel.PageInfo;
import com.xalap.instagram.http.responsemodel.QueryIterator;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "count",
        "page_info",
        "edges"
})
public class EdgeMediaToComment implements QueryIterator<Comment> {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("page_info")
    private PageInfo pageInfo;
    @JsonProperty("edges")
    private List<Edge_> edges = null;

    @JsonProperty("count")
    public Integer getCount() {
        return count;
    }

    @JsonProperty("count")
    public void setCount(Integer count) {
        this.count = count;
    }

    @JsonProperty("page_info")
    public PageInfo getPageInfo() {
        return pageInfo;
    }

    @JsonProperty("page_info")
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    @JsonProperty("edges")
    public List<Edge_> getEdges() {
        return edges;
    }

    @JsonProperty("edges")
    public void setEdges(List<Edge_> edges) {
        this.edges = edges;
    }

    @Override
    public String getLastObjectId() {
        return pageInfo.getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return pageInfo.getHasNextPage();
    }

    @Override
    public List<Comment> next() {
        return getEdges().stream().map(edge_ -> new Comment(edge_.getNode())).collect(Collectors.toList());
    }
}
