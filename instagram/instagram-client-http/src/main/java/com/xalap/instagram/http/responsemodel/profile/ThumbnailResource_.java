
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "src",
        "config_width",
        "config_height"
})
public class ThumbnailResource_ {

    @JsonProperty("src")
    private String src;
    @JsonProperty("config_width")
    private Integer configWidth;
    @JsonProperty("config_height")
    private Integer configHeight;

    @JsonProperty("src")
    public String getSrc() {
        return src;
    }

    @JsonProperty("src")
    public void setSrc(String src) {
        this.src = src;
    }

    @JsonProperty("config_width")
    public Integer getConfigWidth() {
        return configWidth;
    }

    @JsonProperty("config_width")
    public void setConfigWidth(Integer configWidth) {
        this.configWidth = configWidth;
    }

    @JsonProperty("config_height")
    public Integer getConfigHeight() {
        return configHeight;
    }

    @JsonProperty("config_height")
    public void setConfigHeight(Integer configHeight) {
        this.configHeight = configHeight;
    }

}
