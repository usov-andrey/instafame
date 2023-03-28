
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "userMedias",
        "count",
        "page_info"
})
public class Media {

    @JsonProperty("nodes")
    private List<UserMedia> userMedias = null;
    @JsonProperty("count")
    private Integer count;
    @JsonProperty("page_info")
    private PageInfo pageInfo;

    @JsonProperty("nodes")
    public List<UserMedia> getUserMedias() {
        return userMedias;
    }

    @JsonProperty("nodes")
    public void setUserMedias(List<UserMedia> userMedias) {
        this.userMedias = userMedias;
    }

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

    @Override
    public String toString() {
        return this + "";
    }

    public Map<String, UserMedia> nodeMap() {
        return nodeMap(new HashMap<>());
    }

    public Map<String, UserMedia> nodeMap(Map<String, UserMedia> nodeMap) {
        for (UserMedia userMedia : getUserMedias()) {
            nodeMap.put(userMedia.getId(), userMedia);
        }
        return nodeMap;
    }
}
