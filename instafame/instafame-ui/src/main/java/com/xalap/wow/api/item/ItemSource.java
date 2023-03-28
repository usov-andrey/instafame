
package com.xalap.wow.api.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "sourceId",
        "sourceType"
})
public class ItemSource {

    @JsonProperty("sourceId")
    private Integer sourceId;
    @JsonProperty("sourceType")
    private String sourceType;

    @JsonProperty("sourceId")
    public Integer getSourceId() {
        return sourceId;
    }

    @JsonProperty("sourceId")
    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    @JsonProperty("sourceType")
    public String getSourceType() {
        return sourceType;
    }

    @JsonProperty("sourceType")
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

}
