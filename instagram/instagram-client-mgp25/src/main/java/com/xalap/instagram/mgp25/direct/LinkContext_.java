
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "link_url",
        "link_title",
        "link_summary",
        "link_image_url"
})
public class LinkContext_ {

    @JsonProperty("link_url")
    private String linkUrl;
    @JsonProperty("link_title")
    private String linkTitle;
    @JsonProperty("link_summary")
    private String linkSummary;
    @JsonProperty("link_image_url")
    private String linkImageUrl;

    @JsonProperty("link_url")
    public String getLinkUrl() {
        return linkUrl;
    }

    @JsonProperty("link_url")
    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    @JsonProperty("link_title")
    public String getLinkTitle() {
        return linkTitle;
    }

    @JsonProperty("link_title")
    public void setLinkTitle(String linkTitle) {
        this.linkTitle = linkTitle;
    }

    @JsonProperty("link_summary")
    public String getLinkSummary() {
        return linkSummary;
    }

    @JsonProperty("link_summary")
    public void setLinkSummary(String linkSummary) {
        this.linkSummary = linkSummary;
    }

    @JsonProperty("link_image_url")
    public String getLinkImageUrl() {
        return linkImageUrl;
    }

    @JsonProperty("link_image_url")
    public void setLinkImageUrl(String linkImageUrl) {
        this.linkImageUrl = linkImageUrl;
    }

}
