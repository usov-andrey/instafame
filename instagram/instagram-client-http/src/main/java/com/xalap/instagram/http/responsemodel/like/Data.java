
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.like;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "shortcode_media"
})
public class Data {

    @JsonProperty("shortcode_media")
    private LikeShortcodeMedia shortcodeMedia;

    @JsonProperty("shortcode_media")
    public LikeShortcodeMedia getShortcodeMedia() {
        return shortcodeMedia;
    }

    @JsonProperty("shortcode_media")
    public void setShortcodeMedia(LikeShortcodeMedia shortcodeMedia) {
        this.shortcodeMedia = shortcodeMedia;
    }

    @Override
    public String toString() {
        return "Data{" +
                "shortcodeMedia=" + shortcodeMedia +
                '}';
    }
}
