
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "width",
        "height",
        "url",
        "scans_profile",
        "estimated_scans_sizes"
})
public class Candidate {

    @JsonProperty("width")
    private Integer width;
    @JsonProperty("height")
    private Integer height;
    @JsonProperty("url")
    private String url;
    @JsonProperty("scans_profile")
    private String scansProfile;
    @JsonProperty("estimated_scans_sizes")
    private List<Integer> estimatedScansSizes = null;

    @JsonProperty("width")
    public Integer getWidth() {
        return width;
    }

    @JsonProperty("width")
    public void setWidth(Integer width) {
        this.width = width;
    }

    @JsonProperty("height")
    public Integer getHeight() {
        return height;
    }

    @JsonProperty("height")
    public void setHeight(Integer height) {
        this.height = height;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("scans_profile")
    public String getScansProfile() {
        return scansProfile;
    }

    @JsonProperty("scans_profile")
    public void setScansProfile(String scansProfile) {
        this.scansProfile = scansProfile;
    }

    @JsonProperty("estimated_scans_sizes")
    public List<Integer> getEstimatedScansSizes() {
        return estimatedScansSizes;
    }

    @JsonProperty("estimated_scans_sizes")
    public void setEstimatedScansSizes(List<Integer> estimatedScansSizes) {
        this.estimatedScansSizes = estimatedScansSizes;
    }

}
