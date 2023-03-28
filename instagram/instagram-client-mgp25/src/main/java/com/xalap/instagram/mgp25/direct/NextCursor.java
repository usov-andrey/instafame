
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
        "cursor_timestamp_seconds",
        "cursor_thread_v2_id"
})
public class NextCursor {

    @JsonProperty("cursor_timestamp_seconds")
    private String cursorTimestampSeconds;
    @JsonProperty("cursor_thread_v2_id")
    private String cursorThreadV2Id;

    @JsonProperty("cursor_timestamp_seconds")
    public String getCursorTimestampSeconds() {
        return cursorTimestampSeconds;
    }

    @JsonProperty("cursor_timestamp_seconds")
    public void setCursorTimestampSeconds(String cursorTimestampSeconds) {
        this.cursorTimestampSeconds = cursorTimestampSeconds;
    }

    @JsonProperty("cursor_thread_v2_id")
    public String getCursorThreadV2Id() {
        return cursorThreadV2Id;
    }

    @JsonProperty("cursor_thread_v2_id")
    public void setCursorThreadV2Id(String cursorThreadV2Id) {
        this.cursorThreadV2Id = cursorThreadV2Id;
    }

}
