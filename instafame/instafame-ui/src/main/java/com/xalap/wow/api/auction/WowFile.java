
package com.xalap.wow.api.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "url",
        "lastModified"
})
public class WowFile {

    @JsonProperty("url")
    private String url;
    @JsonProperty("lastModified")
    private Long lastModified;

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("lastModified")
    public Long getLastModified() {
        return lastModified;
    }

    @JsonProperty("lastModified")
    public void setLastModified(Long lastModified) {
        this.lastModified = lastModified;
    }

}
