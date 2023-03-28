
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.responsemodel.QueryIterator;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "status"
})
public class MediaLikeResponse implements QueryIterator<String> {

    @JsonProperty("data")
    private Data data;
    @JsonProperty("status")
    private String status;

    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
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
        return "MediaLikeResponse{" +
                "data=" + data +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public String getLastObjectId() {
        return data.getShortcodeMedia().getEdgeLikedBy().getPageInfo().getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return data.getShortcodeMedia().getEdgeLikedBy().getPageInfo().getHasNextPage();
    }

    @Override
    public List<String> next() {
        return data.getShortcodeMedia().getEdgeLikedBy().getEdges().stream().map((edge) -> edge.getNode().getUsername()).collect(Collectors.toList());
    }
}
