
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "threads",
        "has_older",
        "unseen_count",
        "unseen_count_ts",
        "oldest_cursor",
        "prev_cursor",
        "next_cursor",
        "blended_inbox_enabled"
})
public class Inbox {

    @JsonProperty("threads")
    private List<DirectThread> threads = null;
    @JsonProperty("has_older")
    private Boolean hasOlder;
    @JsonProperty("unseen_count")
    private Integer unseenCount;
    @JsonProperty("unseen_count_ts")
    private Long unseenCountTs;
    @JsonProperty("oldest_cursor")
    private String oldestCursor;
    @JsonProperty("prev_cursor")
    private PrevCursor prevCursor;
    @JsonProperty("next_cursor")
    private NextCursor nextCursor;
    @JsonProperty("blended_inbox_enabled")
    private Boolean blendedInboxEnabled;

    @JsonProperty("threads")
    public List<DirectThread> getThreads() {
        return threads;
    }

    @JsonProperty("threads")
    public void setThreads(List<DirectThread> threads) {
        this.threads = threads;
    }

    @JsonProperty("has_older")
    public Boolean getHasOlder() {
        return hasOlder;
    }

    @JsonProperty("has_older")
    public void setHasOlder(Boolean hasOlder) {
        this.hasOlder = hasOlder;
    }

    @JsonProperty("unseen_count")
    public Integer getUnseenCount() {
        return unseenCount;
    }

    @JsonProperty("unseen_count")
    public void setUnseenCount(Integer unseenCount) {
        this.unseenCount = unseenCount;
    }

    @JsonProperty("unseen_count_ts")
    public Long getUnseenCountTs() {
        return unseenCountTs;
    }

    @JsonProperty("unseen_count_ts")
    public void setUnseenCountTs(Long unseenCountTs) {
        this.unseenCountTs = unseenCountTs;
    }

    @JsonProperty("oldest_cursor")
    public String getOldestCursor() {
        return oldestCursor;
    }

    @JsonProperty("oldest_cursor")
    public void setOldestCursor(String oldestCursor) {
        this.oldestCursor = oldestCursor;
    }

    @JsonProperty("prev_cursor")
    public PrevCursor getPrevCursor() {
        return prevCursor;
    }

    @JsonProperty("prev_cursor")
    public void setPrevCursor(PrevCursor prevCursor) {
        this.prevCursor = prevCursor;
    }

    @JsonProperty("next_cursor")
    public NextCursor getNextCursor() {
        return nextCursor;
    }

    @JsonProperty("next_cursor")
    public void setNextCursor(NextCursor nextCursor) {
        this.nextCursor = nextCursor;
    }

    @JsonProperty("blended_inbox_enabled")
    public Boolean getBlendedInboxEnabled() {
        return blendedInboxEnabled;
    }

    @JsonProperty("blended_inbox_enabled")
    public void setBlendedInboxEnabled(Boolean blendedInboxEnabled) {
        this.blendedInboxEnabled = blendedInboxEnabled;
    }

    @Override
    public String toString() {
        return "Inbox{" +
                "threads=" + threads +
                ", hasOlder=" + hasOlder +
                ", unseenCount=" + unseenCount +
                ", unseenCountTs=" + unseenCountTs +
                ", oldestCursor='" + oldestCursor + '\'' +
                ", prevCursor=" + prevCursor +
                ", nextCursor=" + nextCursor +
                ", blendedInboxEnabled=" + blendedInboxEnabled +
                '}';
    }
}
