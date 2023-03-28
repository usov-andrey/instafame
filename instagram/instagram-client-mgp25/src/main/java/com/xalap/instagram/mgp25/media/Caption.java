
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "pk",
        "user_id",
        "text",
        "type",
        "created_at",
        "created_at_utc",
        "content_type",
        "status",
        "bit_flags",
        "did_report_as_spam",
        "share_enabled",
        "user",
        "media_id"
})
public class Caption {

    @JsonProperty("pk")
    private String pk;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("text")
    private String text;
    @JsonProperty("type")
    private Integer type;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("created_at_utc")
    private String createdAtUtc;
    @JsonProperty("content_type")
    private String contentType;
    @JsonProperty("status")
    private String status;
    @JsonProperty("bit_flags")
    private Integer bitFlags;
    @JsonProperty("did_report_as_spam")
    private Boolean didReportAsSpam;
    @JsonProperty("share_enabled")
    private Boolean shareEnabled;
    @JsonProperty("user")
    private UserResponse user;
    @JsonProperty("media_id")
    private String mediaId;

    @JsonProperty("pk")
    public String getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(String pk) {
        this.pk = pk;
    }

    @JsonProperty("user_id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("type")
    public Integer getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(Integer type) {
        this.type = type;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("created_at_utc")
    public String getCreatedAtUtc() {
        return createdAtUtc;
    }

    @JsonProperty("created_at_utc")
    public void setCreatedAtUtc(String createdAtUtc) {
        this.createdAtUtc = createdAtUtc;
    }

    @JsonProperty("content_type")
    public String getContentType() {
        return contentType;
    }

    @JsonProperty("content_type")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("bit_flags")
    public Integer getBitFlags() {
        return bitFlags;
    }

    @JsonProperty("bit_flags")
    public void setBitFlags(Integer bitFlags) {
        this.bitFlags = bitFlags;
    }

    @JsonProperty("did_report_as_spam")
    public Boolean getDidReportAsSpam() {
        return didReportAsSpam;
    }

    @JsonProperty("did_report_as_spam")
    public void setDidReportAsSpam(Boolean didReportAsSpam) {
        this.didReportAsSpam = didReportAsSpam;
    }

    @JsonProperty("share_enabled")
    public Boolean getShareEnabled() {
        return shareEnabled;
    }

    @JsonProperty("share_enabled")
    public void setShareEnabled(Boolean shareEnabled) {
        this.shareEnabled = shareEnabled;
    }

    @JsonProperty("user")
    public UserResponse getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(UserResponse user) {
        this.user = user;
    }

    @JsonProperty("media_id")
    public String getMediaId() {
        return mediaId;
    }

    @JsonProperty("media_id")
    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
