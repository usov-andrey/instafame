
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
        "shortcode_media"
})
public class Graphql {

    @JsonProperty("shortcode_media")
    private ShortcodeMedia shortcodeMedia;

    @JsonProperty("shortcode_media")
    public ShortcodeMedia getShortcodeMedia() {
        return shortcodeMedia;
    }

    @JsonProperty("shortcode_media")
    public void setShortcodeMedia(ShortcodeMedia shortcodeMedia) {
        this.shortcodeMedia = shortcodeMedia;
    }

}
