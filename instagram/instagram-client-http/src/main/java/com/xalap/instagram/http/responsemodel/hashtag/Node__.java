
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.hashtag;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "edge_media_to_caption",
        "shortcode",
        "edge_media_to_comment",
        "taken_at_timestamp",
        "dimensions",
        "display_url",
        "edge_liked_by",
        "edge_media_preview_like",
        "owner",
        "thumbnail_src",
        "thumbnail_resources",
        "is_video",
        "video_view_count"
})
public class Node__ {

    @JsonProperty("id")
    private String id;
    @JsonProperty("edge_media_to_caption")
    private EdgeMediaToCaption_ edgeMediaToCaption;
    @JsonProperty("shortcode")
    private String shortcode;
    @JsonProperty("edge_media_to_comment")
    private EdgeMediaToComment_ edgeMediaToComment;
    @JsonProperty("taken_at_timestamp")
    private Integer takenAtTimestamp;
    @JsonProperty("dimensions")
    private Dimensions_ dimensions;
    @JsonProperty("display_url")
    private String displayUrl;
    @JsonProperty("edge_liked_by")
    private EdgeLikedBy_ edgeLikedBy;
    @JsonProperty("edge_media_preview_like")
    private EdgeMediaPreviewLike_ edgeMediaPreviewLike;
    @JsonProperty("owner")
    private Owner_ owner;
    @JsonProperty("thumbnail_src")
    private String thumbnailSrc;
    @JsonProperty("thumbnail_resources")
    private List<ThumbnailResource_> thumbnailResources = null;
    @JsonProperty("is_video")
    private Boolean isVideo;
    @JsonProperty("video_view_count")
    private Integer videoViewCount;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("edge_media_to_caption")
    public EdgeMediaToCaption_ getEdgeMediaToCaption() {
        return edgeMediaToCaption;
    }

    @JsonProperty("edge_media_to_caption")
    public void setEdgeMediaToCaption(EdgeMediaToCaption_ edgeMediaToCaption) {
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
    public EdgeMediaToComment_ getEdgeMediaToComment() {
        return edgeMediaToComment;
    }

    @JsonProperty("edge_media_to_comment")
    public void setEdgeMediaToComment(EdgeMediaToComment_ edgeMediaToComment) {
        this.edgeMediaToComment = edgeMediaToComment;
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
    public Dimensions_ getDimensions() {
        return dimensions;
    }

    @JsonProperty("dimensions")
    public void setDimensions(Dimensions_ dimensions) {
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
    public EdgeLikedBy_ getEdgeLikedBy() {
        return edgeLikedBy;
    }

    @JsonProperty("edge_liked_by")
    public void setEdgeLikedBy(EdgeLikedBy_ edgeLikedBy) {
        this.edgeLikedBy = edgeLikedBy;
    }

    @JsonProperty("edge_media_preview_like")
    public EdgeMediaPreviewLike_ getEdgeMediaPreviewLike() {
        return edgeMediaPreviewLike;
    }

    @JsonProperty("edge_media_preview_like")
    public void setEdgeMediaPreviewLike(EdgeMediaPreviewLike_ edgeMediaPreviewLike) {
        this.edgeMediaPreviewLike = edgeMediaPreviewLike;
    }

    @JsonProperty("owner")
    public Owner_ getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner_ owner) {
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
    public List<ThumbnailResource_> getThumbnailResources() {
        return thumbnailResources;
    }

    @JsonProperty("thumbnail_resources")
    public void setThumbnailResources(List<ThumbnailResource_> thumbnailResources) {
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

    @JsonProperty("video_view_count")
    public Integer getVideoViewCount() {
        return videoViewCount;
    }

    @JsonProperty("video_view_count")
    public void setVideoViewCount(Integer videoViewCount) {
        this.videoViewCount = videoViewCount;
    }

}
