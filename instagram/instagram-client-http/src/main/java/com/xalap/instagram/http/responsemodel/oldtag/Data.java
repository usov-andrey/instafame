
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.oldtag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "hashtag"
})
public class Data {

    @JsonProperty("hashtag")
    private Hashtag hashtag;

    @JsonProperty("hashtag")
    public Hashtag getHashtag() {
        return hashtag;
    }

    @JsonProperty("hashtag")
    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }

}
