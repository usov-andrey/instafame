
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
        "tag"
})
public class TagSearchResult implements QueryIterator<UserMedia> {

    @JsonProperty("tag")
    private Tag tag;

    @JsonProperty("tag")
    public Tag getTag() {
        return tag;
    }

    @JsonProperty("tag")
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return this + "";
    }

    @Override
    public String getLastObjectId() {
        return tag.getMedia().getPageInfo().getEndCursor();
    }

    @Override
    public boolean hasNext() {
        return tag.getMedia().getPageInfo().getHasNextPage();
    }

    @Override
    public List<UserMedia> next() {
        return tag.getMedia().getUserMedias();
    }
}
