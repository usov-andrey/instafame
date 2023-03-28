
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
        "media",
        "status"
})
public class TagMediaSearch implements QueryIterator<UserMedia> {

    @JsonProperty("media")
    private Media media;
    @JsonProperty("status")
    private String status;

    @JsonProperty("media")
    public Media getMedia() {
        return media;
    }

    @JsonProperty("media")
    public void setMedia(Media media) {
        this.media = media;
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
        return this + "";
    }

    @Override
    public String getLastObjectId() {
        return media.getPageInfo().getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return media.getPageInfo().getHasNextPage();
    }

    @Override
    public List<UserMedia> next() {
        return media.getUserMedias();
    }
}
