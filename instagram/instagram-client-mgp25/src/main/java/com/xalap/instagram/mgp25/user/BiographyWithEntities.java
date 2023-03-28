
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "raw_text",
        "entities"
})
public class BiographyWithEntities {

    @JsonProperty("raw_text")
    private String rawText;
    @JsonProperty("entities")
    private List<Object> entities = null;

    @JsonProperty("raw_text")
    public String getRawText() {
        return rawText;
    }

    @JsonProperty("raw_text")
    public void setRawText(String rawText) {
        this.rawText = rawText;
    }

    @JsonProperty("entities")
    public List<Object> getEntities() {
        return entities;
    }

    @JsonProperty("entities")
    public void setEntities(List<Object> entities) {
        this.entities = entities;
    }

}
