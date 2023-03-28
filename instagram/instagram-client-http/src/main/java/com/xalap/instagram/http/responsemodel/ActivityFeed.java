
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
        "stories"
})
public class ActivityFeed {

    @JsonProperty("stories")
    private List<Story> stories = null;

    @JsonProperty("stories")
    public List<Story> getStories() {
        return stories;
    }

    @JsonProperty("stories")
    public void setStories(List<Story> stories) {
        this.stories = stories;
    }


}
