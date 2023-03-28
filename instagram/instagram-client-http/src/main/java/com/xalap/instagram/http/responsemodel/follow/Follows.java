
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.follow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.framework.utils.CollectionHelper;
import com.xalap.instagram.http.responsemodel.QueryIterator;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "data",
        "status"
})
public class Follows implements QueryIterator<String> {

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
        return data.getUser().getEdgeFollow().getPageInfo().getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return data.getUser().getEdgeFollow().getPageInfo().getHasNextPage();
    }

    @Override
    public List<String> next() {
        return CollectionHelper.newArrayList(data.getUser().getEdgeFollow().getEdges(), (t) -> t.getNode().getUsername());
    }

}
