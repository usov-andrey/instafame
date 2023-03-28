
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.medialist;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.responsemodel.Dimensions;
import com.xalap.instagram.http.responsemodel.media.EdgeMediaToCaption;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "__typename",
        "edge_media_to_caption",
        "shortcode",
        "edge_media_to_comment",
        "comments_disabled",
        "taken_at_timestamp",
        "dimensions",
        "display_url",
        "edge_media_preview_like",
        "owner",
        "thumbnail_src",
        "thumbnail_resources",
        "is_video",
        "video_view_count"
})
public class ShortcodeMediaInList {

    @JsonProperty("id")
    private String id;
    @JsonProperty("__typename")
    private String typename;
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
    @JsonProperty("edge_media_preview_like")
    private EdgeMediaPreviewLike edgeMediaPreviewLike;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("thumbnail_src")
    private String thumbnailSrc;
    @JsonProperty("thumbnail_resources")
    private List<ThumbnailResource> thumbnailResources = null;
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

    @JsonProperty("__typename")
    public String getTypename() {
        return typename;
    }

    @JsonProperty("__typename")
    public void setTypename(String typename) {
        this.typename = typename;
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

    @JsonProperty("edge_media_preview_like")
    public EdgeMediaPreviewLike getEdgeMediaPreviewLike() {
        return edgeMediaPreviewLike;
    }

    @JsonProperty("edge_media_preview_like")
    public void setEdgeMediaPreviewLike(EdgeMediaPreviewLike edgeMediaPreviewLike) {
        this.edgeMediaPreviewLike = edgeMediaPreviewLike;
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

    @JsonProperty("video_view_count")
    public Integer getVideoViewCount() {
        return videoViewCount;
    }

    @JsonProperty("video_view_count")
    public void setVideoViewCount(Integer videoViewCount) {
        this.videoViewCount = videoViewCount;
    }


    @Override
    public String toString() {
        return "ShortcodeMediaInList{" +
                "id='" + id + '\'' +
                ", typename='" + typename + '\'' +
                ", edgeMediaToCaption=" + edgeMediaToCaption +
                ", shortcode='" + shortcode + '\'' +
                ", edgeMediaToComment=" + edgeMediaToComment +
                ", commentsDisabled=" + commentsDisabled +
                ", takenAtTimestamp=" + takenAtTimestamp +
                ", dimensions=" + dimensions +
                ", displayUrl='" + displayUrl + '\'' +
                ", edgeMediaPreviewLike=" + edgeMediaPreviewLike +
                ", owner=" + owner +
                ", thumbnailSrc='" + thumbnailSrc + '\'' +
                ", thumbnailResources=" + thumbnailResources +
                ", isVideo=" + isVideo +
                ", videoViewCount=" + videoViewCount +
                '}';
    }
}
