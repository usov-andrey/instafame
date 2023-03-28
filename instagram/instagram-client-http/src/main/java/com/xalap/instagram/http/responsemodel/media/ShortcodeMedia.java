
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instagram.http.responsemodel.Dimensions;
import com.xalap.instagram.http.responsemodel.Location;

import java.util.List;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShortcodeMedia {

    @JsonProperty("__typename")
    private String typename;
    @JsonProperty("id")
    private String id;
    @JsonProperty("shortcode")
    private String shortcode;
    @JsonProperty("dimensions")
    private Dimensions dimensions;
    @JsonProperty("gating_info")
    private Object gatingInfo;
    @JsonProperty("media_preview")
    private String mediaPreview;
    @JsonProperty("display_url")
    private String displayUrl;
    @JsonProperty("video_url")
    private String videoUrl;
    @JsonProperty("video_view_count")
    private Integer viewViewCount;
    @JsonProperty("video_duration")
    private Float videoDuration;
    @JsonProperty("display_resources")
    private List<DisplayResource> displayResources = null;
    @JsonProperty("is_video")
    private Boolean isVideo;
    @JsonProperty("should_log_client_event")
    private Boolean shouldLogClientEvent;
    @JsonProperty("tracking_token")
    private String trackingToken;
    @JsonProperty("edge_media_to_tagged_user")
    private EdgeMediaToTaggedUser edgeMediaToTaggedUser;
    @JsonProperty("edge_media_to_caption")
    private EdgeMediaToCaption edgeMediaToCaption;
    @JsonProperty("caption_is_edited")
    private Boolean captionIsEdited;
    @JsonProperty("edge_media_to_comment")
    private EdgeMediaToComment edgeMediaToComment;
    @JsonProperty("comments_disabled")
    private Boolean commentsDisabled;
    @JsonProperty("taken_at_timestamp")
    private Integer takenAtTimestamp;
    @JsonProperty("edge_media_preview_like")
    private EdgeMediaPreviewLike edgeMediaPreviewLike;
    @JsonProperty("edge_media_to_sponsor_user")
    private EdgeMediaToSponsorUser edgeMediaToSponsorUser;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("viewer_has_liked")
    private Boolean viewerHasLiked;
    @JsonProperty("viewer_has_saved")
    private Boolean viewerHasSaved;
    @JsonProperty("viewer_has_saved_to_collection")
    private Boolean viewerHasSavedToCollection;
    @JsonProperty("owner")
    private Owner_ owner;
    @JsonProperty("is_ad")
    private Boolean isAd;
    @JsonProperty("edge_web_media_to_related_media")
    private EdgeWebMediaToRelatedMedia edgeWebMediaToRelatedMedia;
    @JsonProperty("edge_sidecar_to_children")
    private EdgeSidecarToChildren edgeSidecarToChildren;

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

    @JsonProperty("shortcode")
    public String getShortcode() {
        return shortcode;
    }

    @JsonProperty("shortcode")
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @JsonProperty("dimensions")
    public Dimensions getDimensions() {
        return dimensions;
    }

    @JsonProperty("dimensions")
    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
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

    @JsonProperty("display_url")
    public String getDisplayUrl() {
        return displayUrl;
    }

    @JsonProperty("display_url")
    public void setDisplayUrl(String displayUrl) {
        this.displayUrl = displayUrl;
    }

    @JsonProperty("display_resources")
    public List<DisplayResource> getDisplayResources() {
        return displayResources;
    }

    @JsonProperty("display_resources")
    public void setDisplayResources(List<DisplayResource> displayResources) {
        this.displayResources = displayResources;
    }

    @JsonProperty("is_video")
    public Boolean getIsVideo() {
        return isVideo;
    }

    @JsonProperty("is_video")
    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    @JsonProperty("should_log_client_event")
    public Boolean getShouldLogClientEvent() {
        return shouldLogClientEvent;
    }

    @JsonProperty("should_log_client_event")
    public void setShouldLogClientEvent(Boolean shouldLogClientEvent) {
        this.shouldLogClientEvent = shouldLogClientEvent;
    }

    @JsonProperty("tracking_token")
    public String getTrackingToken() {
        return trackingToken;
    }

    @JsonProperty("tracking_token")
    public void setTrackingToken(String trackingToken) {
        this.trackingToken = trackingToken;
    }

    @JsonProperty("edge_media_to_tagged_user")
    public EdgeMediaToTaggedUser getEdgeMediaToTaggedUser() {
        return edgeMediaToTaggedUser;
    }

    @JsonProperty("edge_media_to_tagged_user")
    public void setEdgeMediaToTaggedUser(EdgeMediaToTaggedUser edgeMediaToTaggedUser) {
        this.edgeMediaToTaggedUser = edgeMediaToTaggedUser;
    }

    @JsonProperty("edge_media_to_caption")
    public EdgeMediaToCaption getEdgeMediaToCaption() {
        return edgeMediaToCaption;
    }

    @JsonProperty("edge_media_to_caption")
    public void setEdgeMediaToCaption(EdgeMediaToCaption edgeMediaToCaption) {
        this.edgeMediaToCaption = edgeMediaToCaption;
    }

    @JsonProperty("caption_is_edited")
    public Boolean getCaptionIsEdited() {
        return captionIsEdited;
    }

    @JsonProperty("caption_is_edited")
    public void setCaptionIsEdited(Boolean captionIsEdited) {
        this.captionIsEdited = captionIsEdited;
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

    @JsonProperty("edge_media_preview_like")
    public EdgeMediaPreviewLike getEdgeMediaPreviewLike() {
        return edgeMediaPreviewLike;
    }

    @JsonProperty("edge_media_preview_like")
    public void setEdgeMediaPreviewLike(EdgeMediaPreviewLike edgeMediaPreviewLike) {
        this.edgeMediaPreviewLike = edgeMediaPreviewLike;
    }

    @JsonProperty("edge_media_to_sponsor_user")
    public EdgeMediaToSponsorUser getEdgeMediaToSponsorUser() {
        return edgeMediaToSponsorUser;
    }

    @JsonProperty("edge_media_to_sponsor_user")
    public void setEdgeMediaToSponsorUser(EdgeMediaToSponsorUser edgeMediaToSponsorUser) {
        this.edgeMediaToSponsorUser = edgeMediaToSponsorUser;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("viewer_has_liked")
    public Boolean getViewerHasLiked() {
        return viewerHasLiked;
    }

    @JsonProperty("viewer_has_liked")
    public void setViewerHasLiked(Boolean viewerHasLiked) {
        this.viewerHasLiked = viewerHasLiked;
    }

    @JsonProperty("viewer_has_saved")
    public Boolean getViewerHasSaved() {
        return viewerHasSaved;
    }

    @JsonProperty("viewer_has_saved")
    public void setViewerHasSaved(Boolean viewerHasSaved) {
        this.viewerHasSaved = viewerHasSaved;
    }

    @JsonProperty("viewer_has_saved_to_collection")
    public Boolean getViewerHasSavedToCollection() {
        return viewerHasSavedToCollection;
    }

    @JsonProperty("viewer_has_saved_to_collection")
    public void setViewerHasSavedToCollection(Boolean viewerHasSavedToCollection) {
        this.viewerHasSavedToCollection = viewerHasSavedToCollection;
    }

    @JsonProperty("owner")
    public Owner_ getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner_ owner) {
        this.owner = owner;
    }

    @JsonProperty("is_ad")
    public Boolean getIsAd() {
        return isAd;
    }

    @JsonProperty("is_ad")
    public void setIsAd(Boolean isAd) {
        this.isAd = isAd;
    }

    @JsonProperty("edge_web_media_to_related_media")
    public EdgeWebMediaToRelatedMedia getEdgeWebMediaToRelatedMedia() {
        return edgeWebMediaToRelatedMedia;
    }

    @JsonProperty("edge_web_media_to_related_media")
    public void setEdgeWebMediaToRelatedMedia(EdgeWebMediaToRelatedMedia edgeWebMediaToRelatedMedia) {
        this.edgeWebMediaToRelatedMedia = edgeWebMediaToRelatedMedia;
    }

    public String text() {
        for (Edge edge_ : edgeMediaToCaption.getEdges()) {
            String text = edge_.getNode().getText();
            if (!StringHelper.isEmpty(text)) {
                return text;
            }
        }
        return "";
    }

    /**
     * Список отмеченных пользователей
     */
    public List<String> taggedUsers() {
        return edgeMediaToTaggedUser.getEdges().stream().map(edge -> edge.getNode().getUsername()).collect(Collectors.toList());
    }

    public EdgeSidecarToChildren getEdgeSidecarToChildren() {
        return edgeSidecarToChildren;
    }

    public void setEdgeSidecarToChildren(EdgeSidecarToChildren edgeSidecarToChildren) {
        this.edgeSidecarToChildren = edgeSidecarToChildren;
    }

    @Override
    public String toString() {
        return "ShortcodeMedia{" +
                "typename='" + typename + '\'' +
                ", id='" + id + '\'' +
                ", shortcode='" + shortcode + '\'' +
                ", mediaPreview='" + mediaPreview + '\'' +
                ", displayUrl='" + displayUrl + '\'' +
                ", displayResources=" + displayResources +
                ", isVideo=" + isVideo +
                ", takenAtTimestamp=" + takenAtTimestamp +
                ", owner=" + owner +
                '}';
    }
}
