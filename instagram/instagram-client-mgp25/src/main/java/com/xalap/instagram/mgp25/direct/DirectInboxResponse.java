
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
        "viewer",
        "inbox",
        "seq_id",
        "snapshot_at_ms",
        "pending_requests_total",
        "has_pending_top_requests",
        "status"
})
public class DirectInboxResponse {

    @JsonProperty("viewer")
    private Viewer viewer;
    @JsonProperty("inbox")
    private Inbox inbox;
    @JsonProperty("seq_id")
    private String seqId;
    @JsonProperty("snapshot_at_ms")
    private String snapshotAtMs;
    @JsonProperty("pending_requests_total")
    private Integer pendingRequestsTotal;
    @JsonProperty("has_pending_top_requests")
    private Boolean hasPendingTopRequests;
    @JsonProperty("status")
    private String status;

    @JsonProperty("viewer")
    public Viewer getViewer() {
        return viewer;
    }

    @JsonProperty("viewer")
    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    @JsonProperty("inbox")
    public Inbox getInbox() {
        return inbox;
    }

    @JsonProperty("inbox")
    public void setInbox(Inbox inbox) {
        this.inbox = inbox;
    }

    @JsonProperty("seq_id")
    public String getSeqId() {
        return seqId;
    }

    @JsonProperty("seq_id")
    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    @JsonProperty("snapshot_at_ms")
    public String getSnapshotAtMs() {
        return snapshotAtMs;
    }

    @JsonProperty("snapshot_at_ms")
    public void setSnapshotAtMs(String snapshotAtMs) {
        this.snapshotAtMs = snapshotAtMs;
    }

    @JsonProperty("pending_requests_total")
    public Integer getPendingRequestsTotal() {
        return pendingRequestsTotal;
    }

    @JsonProperty("pending_requests_total")
    public void setPendingRequestsTotal(Integer pendingRequestsTotal) {
        this.pendingRequestsTotal = pendingRequestsTotal;
    }

    @JsonProperty("has_pending_top_requests")
    public Boolean getHasPendingTopRequests() {
        return hasPendingTopRequests;
    }

    @JsonProperty("has_pending_top_requests")
    public void setHasPendingTopRequests(Boolean hasPendingTopRequests) {
        this.hasPendingTopRequests = hasPendingTopRequests;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DirectInboxResponse{" +
                "viewer=" + viewer +
                ", inbox=" + inbox +
                ", seqId='" + seqId + '\'' +
                ", snapshotAtMs='" + snapshotAtMs + '\'' +
                ", pendingRequestsTotal=" + pendingRequestsTotal +
                ", hasPendingTopRequests=" + hasPendingTopRequests +
                ", status='" + status + '\'' +
                '}';
    }
}
