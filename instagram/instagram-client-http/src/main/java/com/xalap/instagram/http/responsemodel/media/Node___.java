
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "shortcode",
        "thumbnail_src"
})
public class Node___ {

    @JsonProperty("shortcode")
    private String shortcode;
    @JsonProperty("thumbnail_src")
    private String thumbnailSrc;

    @JsonProperty("shortcode")
    public String getShortcode() {
        return shortcode;
    }

    @JsonProperty("shortcode")
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @JsonProperty("thumbnail_src")
    public String getThumbnailSrc() {
        return thumbnailSrc;
    }

    @JsonProperty("thumbnail_src")
    public void setThumbnailSrc(String thumbnailSrc) {
        this.thumbnailSrc = thumbnailSrc;
    }

}
