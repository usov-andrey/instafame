
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
        "count",
        "page_info",
        "nodes"
})
public class Follow {

    @JsonProperty("count")
    private Integer count;
    @JsonProperty("page_info")
    private PageInfo pageInfo;
    @JsonProperty("nodes")
    private List<FollowNode> nodes = null;

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

    @JsonProperty("nodes")
    public List<FollowNode> getNodes() {
        return nodes;
    }

    @JsonProperty("nodes")
    public void setNodes(List<FollowNode> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
