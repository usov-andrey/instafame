
package com.xalap.wow.api.auction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "files"
})
public class AuctionsFilesResponse {

    @JsonProperty("files")
    private List<WowFile> files = null;

    @JsonProperty("files")
    public List<WowFile> getFiles() {
        return files;
    }

    @JsonProperty("files")
    public void setFiles(List<WowFile> files) {
        this.files = files;
    }

}
