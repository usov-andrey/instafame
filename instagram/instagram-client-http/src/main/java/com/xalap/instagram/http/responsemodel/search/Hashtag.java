
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "hashtag",
        "position"
})
public class Hashtag {

    @JsonProperty("hashtag")
    private Hashtag_ hashtag;
    @JsonProperty("position")
    private Integer position;

    @JsonProperty("hashtag")
    public Hashtag_ getHashtag() {
        return hashtag;
    }

    @JsonProperty("hashtag")
    public void setHashtag(Hashtag_ hashtag) {
        this.hashtag = hashtag;
    }

    @JsonProperty("position")
    public Integer getPosition() {
        return position;
    }

    @JsonProperty("position")
    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
