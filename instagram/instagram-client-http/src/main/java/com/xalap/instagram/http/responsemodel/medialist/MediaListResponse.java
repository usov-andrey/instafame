
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.medialist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.http.responsemodel.QueryIterator;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "status"
})
public class MediaListResponse implements QueryIterator<Media> {

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
    public String getLastObjectId() {
        return data.getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return data.getUser().getEdgeOwnerToTimelineMedia().getPageInfo().getHasNextPage();
    }

    @Override
    public List<Media> next() {
        return data.getUser().getEdgeOwnerToTimelineMedia().getEdges().stream()
                .map(new Function<Edge, Media>() {
                    @Override
                    public Media apply(Edge edge) {
                        return null;
                    }
                }).collect(Collectors.toList());
    }
}
