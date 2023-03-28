
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.direct;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "thread_id",
        "thread_v2_id",
        "users",
        "left_users",
        "admin_user_ids",
        "items",
        "last_activity_at",
        "muted",
        "is_pin",
        "named",
        "canonical",
        "pending",
        "archived",
        "valued_request",
        "thread_type",
        "viewer_id",
        "thread_title",
        "pending_score",
        "folder",
        "vc_muted",
        "is_group",
        "mentions_muted",
        "approval_required_for_new_members",
        "input_mode",
        "business_thread_folder",
        "inviter",
        "has_older",
        "has_newer",
        "last_seen_at",
        "newest_cursor",
        "oldest_cursor",
        "is_spam",
        "last_permanent_item"
})
public class DirectThread {

    @JsonProperty("thread_id")
    private String threadId;
    @JsonProperty("thread_v2_id")
    private String threadV2Id;
    @JsonProperty("users")
    private List<User> users = null;
    @JsonProperty("left_users")
    private List<Object> leftUsers = null;
    @JsonProperty("admin_user_ids")
    private List<Object> adminUserIds = null;
    @JsonProperty("items")
    private List<Item> items = null;
    @JsonProperty("last_activity_at")
    private String lastActivityAt;
    @JsonProperty("muted")
    private Boolean muted;
    @JsonProperty("is_pin")
    private Boolean isPin;
    @JsonProperty("named")
    private Boolean named;
    @JsonProperty("canonical")
    private Boolean canonical;
    @JsonProperty("pending")
    private Boolean pending;
    @JsonProperty("archived")
    private Boolean archived;
    @JsonProperty("valued_request")
    private Boolean valuedRequest;
    @JsonProperty("thread_type")
    private String threadType;
    @JsonProperty("viewer_id")
    private String viewerId;
    @JsonProperty("thread_title")
    private String threadTitle;
    @JsonProperty("pending_score")
    private String pendingScore;
    @JsonProperty("folder")
    private Integer folder;
    @JsonProperty("vc_muted")
    private Boolean vcMuted;
    @JsonProperty("is_group")
    private Boolean isGroup;
    @JsonProperty("mentions_muted")
    private Boolean mentionsMuted;
    @JsonProperty("approval_required_for_new_members")
    private Boolean approvalRequiredForNewMembers;
    @JsonProperty("input_mode")
    private Integer inputMode;
    @JsonProperty("business_thread_folder")
    private Integer businessThreadFolder;
    @JsonProperty("inviter")
    private Inviter inviter;
    @JsonProperty("has_older")
    private Boolean hasOlder;
    @JsonProperty("has_newer")
    private Boolean hasNewer;
    @JsonProperty("last_seen_at")
    private LastSeenAt lastSeenAt;
    @JsonProperty("newest_cursor")
    private String newestCursor;
    @JsonProperty("oldest_cursor")
    private String oldestCursor;
    @JsonProperty("is_spam")
    private Boolean isSpam;
    @JsonProperty("last_permanent_item")
    private LastPermanentItem lastPermanentItem;

    @JsonProperty("thread_id")
    public String getThreadId() {
        return threadId;
    }

    @JsonProperty("thread_id")
    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    @JsonProperty("thread_v2_id")
    public String getThreadV2Id() {
        return threadV2Id;
    }

    @JsonProperty("thread_v2_id")
    public void setThreadV2Id(String threadV2Id) {
        this.threadV2Id = threadV2Id;
    }

    @JsonProperty("users")
    public List<User> getUsers() {
        return users;
    }

    @JsonProperty("users")
    public void setUsers(List<User> users) {
        this.users = users;
    }

    @JsonProperty("left_users")
    public List<Object> getLeftUsers() {
        return leftUsers;
    }

    @JsonProperty("left_users")
    public void setLeftUsers(List<Object> leftUsers) {
        this.leftUsers = leftUsers;
    }

    @JsonProperty("admin_user_ids")
    public List<Object> getAdminUserIds() {
        return adminUserIds;
    }

    @JsonProperty("admin_user_ids")
    public void setAdminUserIds(List<Object> adminUserIds) {
        this.adminUserIds = adminUserIds;
    }

    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

    @JsonProperty("last_activity_at")
    public String getLastActivityAt() {
        return lastActivityAt;
    }

    @JsonProperty("last_activity_at")
    public void setLastActivityAt(String lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public Date lastActivity() {
        return new Date(Long.parseLong(getLastActivityAt()) / 1000);
    }

    @JsonProperty("muted")
    public Boolean getMuted() {
        return muted;
    }

    @JsonProperty("muted")
    public void setMuted(Boolean muted) {
        this.muted = muted;
    }

    @JsonProperty("is_pin")
    public Boolean getIsPin() {
        return isPin;
    }

    @JsonProperty("is_pin")
    public void setIsPin(Boolean isPin) {
        this.isPin = isPin;
    }

    @JsonProperty("named")
    public Boolean getNamed() {
        return named;
    }

    @JsonProperty("named")
    public void setNamed(Boolean named) {
        this.named = named;
    }

    @JsonProperty("canonical")
    public Boolean getCanonical() {
        return canonical;
    }

    @JsonProperty("canonical")
    public void setCanonical(Boolean canonical) {
        this.canonical = canonical;
    }

    @JsonProperty("pending")
    public Boolean getPending() {
        return pending;
    }

    @JsonProperty("pending")
    public void setPending(Boolean pending) {
        this.pending = pending;
    }

    @JsonProperty("archived")
    public Boolean getArchived() {
        return archived;
    }

    @JsonProperty("archived")
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    @JsonProperty("valued_request")
    public Boolean getValuedRequest() {
        return valuedRequest;
    }

    @JsonProperty("valued_request")
    public void setValuedRequest(Boolean valuedRequest) {
        this.valuedRequest = valuedRequest;
    }

    @JsonProperty("thread_type")
    public String getThreadType() {
        return threadType;
    }

    @JsonProperty("thread_type")
    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    @JsonProperty("viewer_id")
    public String getViewerId() {
        return viewerId;
    }

    @JsonProperty("viewer_id")
    public void setViewerId(String viewerId) {
        this.viewerId = viewerId;
    }

    @JsonProperty("thread_title")
    public String getThreadTitle() {
        return threadTitle;
    }

    @JsonProperty("thread_title")
    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    @JsonProperty("pending_score")
    public String getPendingScore() {
        return pendingScore;
    }

    @JsonProperty("pending_score")
    public void setPendingScore(String pendingScore) {
        this.pendingScore = pendingScore;
    }

    @JsonProperty("folder")
    public Integer getFolder() {
        return folder;
    }

    @JsonProperty("folder")
    public void setFolder(Integer folder) {
        this.folder = folder;
    }

    @JsonProperty("vc_muted")
    public Boolean getVcMuted() {
        return vcMuted;
    }

    @JsonProperty("vc_muted")
    public void setVcMuted(Boolean vcMuted) {
        this.vcMuted = vcMuted;
    }

    @JsonProperty("is_group")
    public Boolean getIsGroup() {
        return isGroup;
    }

    @JsonProperty("is_group")
    public void setIsGroup(Boolean isGroup) {
        this.isGroup = isGroup;
    }

    @JsonProperty("mentions_muted")
    public Boolean getMentionsMuted() {
        return mentionsMuted;
    }

    @JsonProperty("mentions_muted")
    public void setMentionsMuted(Boolean mentionsMuted) {
        this.mentionsMuted = mentionsMuted;
    }

    @JsonProperty("approval_required_for_new_members")
    public Boolean getApprovalRequiredForNewMembers() {
        return approvalRequiredForNewMembers;
    }

    @JsonProperty("approval_required_for_new_members")
    public void setApprovalRequiredForNewMembers(Boolean approvalRequiredForNewMembers) {
        this.approvalRequiredForNewMembers = approvalRequiredForNewMembers;
    }

    @JsonProperty("input_mode")
    public Integer getInputMode() {
        return inputMode;
    }

    @JsonProperty("input_mode")
    public void setInputMode(Integer inputMode) {
        this.inputMode = inputMode;
    }

    @JsonProperty("business_thread_folder")
    public Integer getBusinessThreadFolder() {
        return businessThreadFolder;
    }

    @JsonProperty("business_thread_folder")
    public void setBusinessThreadFolder(Integer businessThreadFolder) {
        this.businessThreadFolder = businessThreadFolder;
    }

    @JsonProperty("inviter")
    public Inviter getInviter() {
        return inviter;
    }

    @JsonProperty("inviter")
    public void setInviter(Inviter inviter) {
        this.inviter = inviter;
    }

    @JsonProperty("has_older")
    public Boolean getHasOlder() {
        return hasOlder;
    }

    @JsonProperty("has_older")
    public void setHasOlder(Boolean hasOlder) {
        this.hasOlder = hasOlder;
    }

    @JsonProperty("has_newer")
    public Boolean getHasNewer() {
        return hasNewer;
    }

    @JsonProperty("has_newer")
    public void setHasNewer(Boolean hasNewer) {
        this.hasNewer = hasNewer;
    }

    @JsonProperty("last_seen_at")
    public LastSeenAt getLastSeenAt() {
        return lastSeenAt;
    }

    @JsonProperty("last_seen_at")
    public void setLastSeenAt(LastSeenAt lastSeenAt) {
        this.lastSeenAt = lastSeenAt;
    }

    @JsonProperty("newest_cursor")
    public String getNewestCursor() {
        return newestCursor;
    }

    @JsonProperty("newest_cursor")
    public void setNewestCursor(String newestCursor) {
        this.newestCursor = newestCursor;
    }

    @JsonProperty("oldest_cursor")
    public String getOldestCursor() {
        return oldestCursor;
    }

    @JsonProperty("oldest_cursor")
    public void setOldestCursor(String oldestCursor) {
        this.oldestCursor = oldestCursor;
    }

    @JsonProperty("is_spam")
    public Boolean getIsSpam() {
        return isSpam;
    }

    @JsonProperty("is_spam")
    public void setIsSpam(Boolean isSpam) {
        this.isSpam = isSpam;
    }

    @JsonProperty("last_permanent_item")
    public LastPermanentItem getLastPermanentItem() {
        return lastPermanentItem;
    }

    @JsonProperty("last_permanent_item")
    public void setLastPermanentItem(LastPermanentItem lastPermanentItem) {
        this.lastPermanentItem = lastPermanentItem;
    }

    @Override
    public String toString() {
        return "Thread{" +
                "threadId='" + threadId + '\'' +
                ", threadV2Id='" + threadV2Id + '\'' +
                ", users=" + users +
                ", leftUsers=" + leftUsers +
                ", adminUserIds=" + adminUserIds +
                ", items=" + items +
                ", lastActivityAt='" + lastActivityAt + '\'' +
                ", muted=" + muted +
                ", isPin=" + isPin +
                ", named=" + named +
                ", canonical=" + canonical +
                ", pending=" + pending +
                ", archived=" + archived +
                ", valuedRequest=" + valuedRequest +
                ", threadType='" + threadType + '\'' +
                ", viewerId='" + viewerId + '\'' +
                ", threadTitle='" + threadTitle + '\'' +
                ", pendingScore='" + pendingScore + '\'' +
                ", folder=" + folder +
                ", vcMuted=" + vcMuted +
                ", isGroup=" + isGroup +
                ", mentionsMuted=" + mentionsMuted +
                ", approvalRequiredForNewMembers=" + approvalRequiredForNewMembers +
                ", inputMode=" + inputMode +
                ", businessThreadFolder=" + businessThreadFolder +
                ", inviter=" + inviter +
                ", hasOlder=" + hasOlder +
                ", hasNewer=" + hasNewer +
                ", lastSeenAt=" + lastSeenAt +
                ", newestCursor='" + newestCursor + '\'' +
                ", oldestCursor='" + oldestCursor + '\'' +
                ", isSpam=" + isSpam +
                ", lastPermanentItem=" + lastPermanentItem +
                '}';
    }
}
