
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Node {

    @JsonProperty("__typename")
    private String typename;
    @JsonProperty("id")
    private String id;
    @JsonProperty("edge_media_to_caption")
    private EdgeMediaToCaption edgeMediaToCaption;
    @JsonProperty("shortcode")
    private String shortcode;
    @JsonProperty("edge_media_to_comment")
    private EdgeMediaToComment edgeMediaToComment;
    @JsonProperty("comments_disabled")
    private Boolean commentsDisabled;
    @JsonProperty("taken_at_timestamp")
    private Integer takenAtTimestamp;
    @JsonProperty("dimensions")
    private Dimensions dimensions;
    @JsonProperty("display_url")
    private String displayUrl;
    @JsonProperty("edge_liked_by")
    private EdgeLikedBy edgeLikedBy;
    @JsonProperty("edge_media_preview_like")
    private EdgeMediaPreviewLike edgeMediaPreviewLike;
    @JsonProperty("gating_info")
    private Object gatingInfo;
    @JsonProperty("media_preview")
    private String mediaPreview;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("thumbnail_src")
    private String thumbnailSrc;
    @JsonProperty("thumbnail_resources")
    private List<ThumbnailResource> thumbnailResources = null;
    @JsonProperty("is_video")
    private Boolean isVideo;
    @JsonProperty("video_url")
    private String videoUrl;
    @JsonProperty("video_view_count")
    private Integer viewViewCount;
    @JsonProperty("video_duration")
    private Float videoDuration;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getViewViewCount() {
        return viewViewCount;
    }

    public void setViewViewCount(Integer viewViewCount) {
        this.viewViewCount = viewViewCount;
    }

    public Float getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Float videoDuration) {
        this.videoDuration = videoDuration;
    }

    @JsonProperty("__typename")
    public String getTypename() {
        return typename;
    }

    @JsonProperty("__typename")
    public void setTypename(String typename) {
        this.typename = typename;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("edge_media_to_caption")
    public EdgeMediaToCaption getEdgeMediaToCaption() {
        return edgeMediaToCaption;
    }

    @JsonProperty("edge_media_to_caption")
    public void setEdgeMediaToCaption(EdgeMediaToCaption edgeMediaToCaption) {
        this.edgeMediaToCaption = edgeMediaToCaption;
    }

    @JsonProperty("shortcode")
    public String getShortcode() {
        return shortcode;
    }

    @JsonProperty("shortcode")
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @JsonProperty("edge_media_to_comment")
    public EdgeMediaToComment getEdgeMediaToComment() {
        return edgeMediaToComment;
    }

    @JsonProperty("edge_media_to_comment")
    public void setEdgeMediaToComment(EdgeMediaToComment edgeMediaToComment) {
        this.edgeMediaToComment = edgeMediaToComment;
    }

    @JsonProperty("comments_disabled")
    public Boolean getCommentsDisabled() {
        return commentsDisabled;
    }

    @JsonProperty("comments_disabled")
    public void setCommentsDisabled(Boolean commentsDisabled) {
        this.commentsDisabled = commentsDisabled;
    }

    @JsonProperty("taken_at_timestamp")
    public Integer getTakenAtTimestamp() {
        return takenAtTimestamp;
    }

    @JsonProperty("taken_at_timestamp")
    public void setTakenAtTimestamp(Integer takenAtTimestamp) {
        this.takenAtTimestamp = takenAtTimestamp;
    }

    @JsonProperty("dimensions")
    public Dimensions getDimensions() {
        return dimensions;
    }

    @JsonProperty("dimensions")
    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
    }

    @JsonProperty("display_url")
    public String getDisplayUrl() {
        return displayUrl;
    }

    @JsonProperty("display_url")
    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    @JsonProperty("edge_liked_by")
    public EdgeLikedBy getEdgeLikedBy() {
        return edgeLikedBy;
    }

    @JsonProperty("edge_liked_by")
    public void setEdgeLikedBy(EdgeLikedBy edgeLikedBy) {
        this.edgeLikedBy = edgeLikedBy;
    }

    @JsonProperty("edge_media_preview_like")
    public EdgeMediaPreviewLike getEdgeMediaPreviewLike() {
        return edgeMediaPreviewLike;
    }

    @JsonProperty("edge_media_preview_like")
    public void setEdgeMediaPreviewLike(EdgeMediaPreviewLike edgeMediaPreviewLike) {
        this.edgeMediaPreviewLike = edgeMediaPreviewLike;
    }

    @JsonProperty("gating_info")
    public Object getGatingInfo() {
        return gatingInfo;
    }

    @JsonProperty("gating_info")
    public void setGatingInfo(Object gatingInfo) {
        this.gatingInfo = gatingInfo;
    }

    @JsonProperty("media_preview")
    public String getMediaPreview() {
        return mediaPreview;
    }

    @JsonProperty("media_preview")
    public void setMediaPreview(String mediaPreview) {
        this.mediaPreview = mediaPreview;
    }

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @JsonProperty("thumbnail_src")
    public String getThumbnailSrc() {
        return thumbnailSrc;
    }

    @JsonProperty("thumbnail_src")
    public void setThumbnailSrc(String thumbnailSrc) {
        this.thumbnailSrc = thumbnailSrc;
    }

    @JsonProperty("thumbnail_resources")
    public List<ThumbnailResource> getThumbnailResources() {
        return thumbnailResources;
    }

    @JsonProperty("thumbnail_resources")
    public void setThumbnailResources(List<ThumbnailResource> thumbnailResources) {
        this.thumbnailResources = thumbnailResources;
    }

    @JsonProperty("is_video")
    public Boolean getIsVideo() {
        return isVideo;
    }

    @JsonProperty("is_video")
    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

}
