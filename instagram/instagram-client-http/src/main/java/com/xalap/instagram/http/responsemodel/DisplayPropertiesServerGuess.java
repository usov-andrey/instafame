
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pixel_ratio",
        "viewport_width"
})
public class DisplayPropertiesServerGuess {

    @JsonProperty("pixel_ratio")
    private Double pixelRatio;
    @JsonProperty("viewport_width")
    private Integer viewportWidth;

    @JsonProperty("pixel_ratio")
    public Double getPixelRatio() {
        return pixelRatio;
    }

    @JsonProperty("pixel_ratio")
    public void setPixelRatio(Double pixelRatio) {
        this.pixelRatio = pixelRatio;
    }

    @JsonProperty("viewport_width")
    public Integer getViewportWidth() {
        return viewportWidth;
    }

    @JsonProperty("viewport_width")
    public void setViewportWidth(Integer viewportWidth) {
        this.viewportWidth = viewportWidth;
    }

    @Override
    public String toString() {
        return this + "";
    }

}
