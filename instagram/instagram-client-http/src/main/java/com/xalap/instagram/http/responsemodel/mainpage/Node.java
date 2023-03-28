
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.mainpage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.xalap.instagram.http.responsemodel.Dimensions;
import com.xalap.instagram.http.responsemodel.media.EdgeMediaToCaption;
import com.xalap.instagram.http.responsemodel.medialist.EdgeMediaPreviewLike;
import com.xalap.instagram.http.responsemodel.medialist.EdgeMediaToComment;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "__typename",
        "id",
        "dimensions",
        "display_url",
        "display_resources",
        "is_video",
        "edge_media_to_tagged_user",
        "attribution",
        "shortcode",
        "edge_media_to_caption",
        "edge_media_to_comment",
        "comments_disabled",
        "taken_at_timestamp",
        "edge_media_preview_like",
        "edge_media_to_sponsor_user",
        "location",
        "viewer_has_liked",
        "owner"
})
public class Node {

    @JsonProperty("__typename")
    private String typename;
    @JsonProperty("id")
    private String id;
    @JsonProperty("dimensions")
    private Dimensions dimensions;
    @JsonProperty("display_url")
    private String displayUrl;
    @JsonProperty("display_resources")
    private List<Object> displayResources = null;
    @JsonProperty("is_video")
    private Boolean isVideo;
    @JsonProperty("edge_media_to_tagged_user")
    private EdgeMediaToTaggedUser edgeMediaToTaggedUser;
    @JsonProperty("attribution")
    private Object attribution;
    @JsonProperty("shortcode")
    private String shortcode;
    @JsonProperty("edge_media_to_caption")
    private EdgeMediaToCaption edgeMediaToCaption;
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
    private Object location;
    @JsonProperty("viewer_has_liked")
    private Boolean viewerHasLiked;
    @JsonProperty("owner")
    private Owner owner;

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

    @JsonProperty("display_resources")
    public List<Object> getDisplayResources() {
        return displayResources;
    }

    @JsonProperty("display_resources")
    public void setDisplayResources(List<Object> displayResources) {
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

    @JsonProperty("edge_media_to_tagged_user")
    public EdgeMediaToTaggedUser getEdgeMediaToTaggedUser() {
        return edgeMediaToTaggedUser;
    }

    @JsonProperty("edge_media_to_tagged_user")
    public void setEdgeMediaToTaggedUser(EdgeMediaToTaggedUser edgeMediaToTaggedUser) {
        this.edgeMediaToTaggedUser = edgeMediaToTaggedUser;
    }

    @JsonProperty("attribution")
    public Object getAttribution() {
        return attribution;
    }

    @JsonProperty("attribution")
    public void setAttribution(Object attribution) {
        this.attribution = attribution;
    }

    @JsonProperty("shortcode")
    public String getShortcode() {
        return shortcode;
    }

    @JsonProperty("shortcode")
    public void setShortcode(String shortcode) {
        this.shortcode = shortcode;
    }

    @JsonProperty("edge_media_to_caption")
    public EdgeMediaToCaption getEdgeMediaToCaption() {
        return edgeMediaToCaption;
    }

    @JsonProperty("edge_media_to_caption")
    public void setEdgeMediaToCaption(EdgeMediaToCaption edgeMediaToCaption) {
        this.edgeMediaToCaption = edgeMediaToCaption;
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
    public Object getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Object location) {
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

    @JsonProperty("owner")
    public Owner getOwner() {
        return owner;
    }

    @JsonProperty("owner")
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

}
