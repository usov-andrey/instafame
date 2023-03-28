
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.mgp25.media;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "taken_at",
        "pk",
        "id",
        "device_timestamp",
        "media_type",
        "code",
        "client_cache_key",
        "filter_type",
        "image_versions2",
        "original_width",
        "original_height",
        "user",
        "can_viewer_reshare",
        "caption_is_edited",
        "comment_likes_enabled",
        "comment_threading_enabled",
        "has_more_comments",
        "max_num_visible_preview_comments",
        "can_view_more_preview_comments",
        "comment_count",
        "inline_composer_display_condition",
        "inline_composer_imp_trigger_time",
        "like_count",
        "has_liked",
        "top_likers",
        "photo_of_you",
        "can_see_insights_as_brand",
        "caption",
        "can_viewer_save",
        "organic_tracking_token",
        "carousel_media_count",
        "carousel_media",
        "location",
        "lat",
        "lng",
        "view_count"
})
public class MediaResponse {

    @JsonProperty("taken_at")
    private String takenAt;
    @JsonProperty("pk")
    private String pk;
    @JsonProperty("id")
    private String id;
    @JsonProperty("device_timestamp")
    private String deviceTimestamp;
    @JsonProperty("media_type")
    private Integer mediaType;
    @JsonProperty("code")
    private String code;
    @JsonProperty("client_cache_key")
    private String clientCacheKey;
    @JsonProperty("filter_type")
    private Integer filterType;
    @JsonProperty("image_versions2")
    private ImageVersions2 imageVersions2;
    @JsonProperty("original_width")
    private Integer originalWidth;
    @JsonProperty("original_height")
    private Integer originalHeight;
    @JsonProperty("user")
    private UserResponse user;
    @JsonProperty("can_viewer_reshare")
    private Boolean canViewerReshare;
    @JsonProperty("caption_is_edited")
    private Boolean captionIsEdited;
    @JsonProperty("comment_likes_enabled")
    private Boolean commentLikesEnabled;
    @JsonProperty("comment_threading_enabled")
    private Boolean commentThreadingEnabled;
    @JsonProperty("has_more_comments")
    private Boolean hasMoreComments;
    @JsonProperty("max_num_visible_preview_comments")
    private Integer maxNumVisiblePreviewComments;
    @JsonProperty("can_view_more_preview_comments")
    private Boolean canViewMorePreviewComments;
    @JsonProperty("comment_count")
    private Integer commentCount;
    @JsonProperty("inline_composer_display_condition")
    private String inlineComposerDisplayCondition;
    @JsonProperty("inline_composer_imp_trigger_time")
    private Integer inlineComposerImpTriggerTime;
    @JsonProperty("like_count")
    private Integer likeCount;
    @JsonProperty("has_liked")
    private Boolean hasLiked;
    @JsonProperty("top_likers")
    private List<Object> topLikers = null;
    @JsonProperty("photo_of_you")
    private Boolean photoOfYou;
    @JsonProperty("can_see_insights_as_brand")
    private Boolean canSeeInsightsAsBrand;
    @JsonProperty("caption")
    private Caption caption;
    @JsonProperty("can_viewer_save")
    private Boolean canViewerSave;
    @JsonProperty("organic_tracking_token")
    private String organicTrackingToken;
    @JsonProperty("carousel_media_count")
    private Integer carouselMediaCount;
    @JsonProperty("carousel_media")
    private List<CarouselMedium> carouselMedia = null;
    @JsonProperty("location")
    private Location location;
    @JsonProperty("lat")
    private Double lat;
    @JsonProperty("lng")
    private Double lng;
    @JsonProperty("view_count")
    private Integer viewCount;

    @JsonProperty("taken_at")
    public String getTakenAt() {
        return takenAt;
    }

    @JsonProperty("taken_at")
    public void setTakenAt(String takenAt) {
        this.takenAt = takenAt;
    }

    @JsonProperty("pk")
    public String getPk() {
        return pk;
    }

    @JsonProperty("pk")
    public void setPk(String pk) {
        this.pk = pk;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("device_timestamp")
    public String getDeviceTimestamp() {
        return deviceTimestamp;
    }

    @JsonProperty("device_timestamp")
    public void setDeviceTimestamp(String deviceTimestamp) {
        this.deviceTimestamp = deviceTimestamp;
    }

    @JsonProperty("media_type")
    public Integer getMediaType() {
        return mediaType;
    }

    @JsonProperty("media_type")
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("client_cache_key")
    public String getClientCacheKey() {
        return clientCacheKey;
    }

    @JsonProperty("client_cache_key")
    public void setClientCacheKey(String clientCacheKey) {
        this.clientCacheKey = clientCacheKey;
    }

    @JsonProperty("filter_type")
    public Integer getFilterType() {
        return filterType;
    }

    @JsonProperty("filter_type")
    public void setFilterType(Integer filterType) {
        this.filterType = filterType;
    }

    @JsonProperty("image_versions2")
    public ImageVersions2 getImageVersions2() {
        return imageVersions2;
    }

    @JsonProperty("image_versions2")
    public void setImageVersions2(ImageVersions2 imageVersions2) {
        this.imageVersions2 = imageVersions2;
    }

    @JsonProperty("original_width")
    public Integer getOriginalWidth() {
        return originalWidth;
    }

    @JsonProperty("original_width")
    public void setOriginalWidth(Integer originalWidth) {
        this.originalWidth = originalWidth;
    }

    @JsonProperty("original_height")
    public Integer getOriginalHeight() {
        return originalHeight;
    }

    @JsonProperty("original_height")
    public void setOriginalHeight(Integer originalHeight) {
        this.originalHeight = originalHeight;
    }

    @JsonProperty("user")
    public UserResponse getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(UserResponse user) {
        this.user = user;
    }

    @JsonProperty("can_viewer_reshare")
    public Boolean getCanViewerReshare() {
        return canViewerReshare;
    }

    @JsonProperty("can_viewer_reshare")
    public void setCanViewerReshare(Boolean canViewerReshare) {
        this.canViewerReshare = canViewerReshare;
    }

    @JsonProperty("caption_is_edited")
    public Boolean getCaptionIsEdited() {
        return captionIsEdited;
    }

    @JsonProperty("caption_is_edited")
    public void setCaptionIsEdited(Boolean captionIsEdited) {
        this.captionIsEdited = captionIsEdited;
    }

    @JsonProperty("comment_likes_enabled")
    public Boolean getCommentLikesEnabled() {
        return commentLikesEnabled;
    }

    @JsonProperty("comment_likes_enabled")
    public void setCommentLikesEnabled(Boolean commentLikesEnabled) {
        this.commentLikesEnabled = commentLikesEnabled;
    }

    @JsonProperty("comment_threading_enabled")
    public Boolean getCommentThreadingEnabled() {
        return commentThreadingEnabled;
    }

    @JsonProperty("comment_threading_enabled")
    public void setCommentThreadingEnabled(Boolean commentThreadingEnabled) {
        this.commentThreadingEnabled = commentThreadingEnabled;
    }

    @JsonProperty("has_more_comments")
    public Boolean getHasMoreComments() {
        return hasMoreComments;
    }

    @JsonProperty("has_more_comments")
    public void setHasMoreComments(Boolean hasMoreComments) {
        this.hasMoreComments = hasMoreComments;
    }

    @JsonProperty("max_num_visible_preview_comments")
    public Integer getMaxNumVisiblePreviewComments() {
        return maxNumVisiblePreviewComments;
    }

    @JsonProperty("max_num_visible_preview_comments")
    public void setMaxNumVisiblePreviewComments(Integer maxNumVisiblePreviewComments) {
        this.maxNumVisiblePreviewComments = maxNumVisiblePreviewComments;
    }

    @JsonProperty("can_view_more_preview_comments")
    public Boolean getCanViewMorePreviewComments() {
        return canViewMorePreviewComments;
    }

    @JsonProperty("can_view_more_preview_comments")
    public void setCanViewMorePreviewComments(Boolean canViewMorePreviewComments) {
        this.canViewMorePreviewComments = canViewMorePreviewComments;
    }

    @JsonProperty("comment_count")
    public Integer getCommentCount() {
        return commentCount;
    }

    @JsonProperty("comment_count")
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    @JsonProperty("inline_composer_display_condition")
    public String getInlineComposerDisplayCondition() {
        return inlineComposerDisplayCondition;
    }

    @JsonProperty("inline_composer_display_condition")
    public void setInlineComposerDisplayCondition(String inlineComposerDisplayCondition) {
        this.inlineComposerDisplayCondition = inlineComposerDisplayCondition;
    }

    @JsonProperty("inline_composer_imp_trigger_time")
    public Integer getInlineComposerImpTriggerTime() {
        return inlineComposerImpTriggerTime;
    }

    @JsonProperty("inline_composer_imp_trigger_time")
    public void setInlineComposerImpTriggerTime(Integer inlineComposerImpTriggerTime) {
        this.inlineComposerImpTriggerTime = inlineComposerImpTriggerTime;
    }

    @JsonProperty("like_count")
    public Integer getLikeCount() {
        return likeCount;
    }

    @JsonProperty("like_count")
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    @JsonProperty("has_liked")
    public Boolean getHasLiked() {
        return hasLiked;
    }

    @JsonProperty("has_liked")
    public void setHasLiked(Boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    @JsonProperty("top_likers")
    public List<Object> getTopLikers() {
        return topLikers;
    }

    @JsonProperty("top_likers")
    public void setTopLikers(List<Object> topLikers) {
        this.topLikers = topLikers;
    }

    @JsonProperty("photo_of_you")
    public Boolean getPhotoOfYou() {
        return photoOfYou;
    }

    @JsonProperty("photo_of_you")
    public void setPhotoOfYou(Boolean photoOfYou) {
        this.photoOfYou = photoOfYou;
    }

    @JsonProperty("can_see_insights_as_brand")
    public Boolean getCanSeeInsightsAsBrand() {
        return canSeeInsightsAsBrand;
    }

    @JsonProperty("can_see_insights_as_brand")
    public void setCanSeeInsightsAsBrand(Boolean canSeeInsightsAsBrand) {
        this.canSeeInsightsAsBrand = canSeeInsightsAsBrand;
    }

    @JsonProperty("caption")
    public Caption getCaption() {
        return caption;
    }

    @JsonProperty("caption")
    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    @JsonProperty("can_viewer_save")
    public Boolean getCanViewerSave() {
        return canViewerSave;
    }

    @JsonProperty("can_viewer_save")
    public void setCanViewerSave(Boolean canViewerSave) {
        this.canViewerSave = canViewerSave;
    }

    @JsonProperty("organic_tracking_token")
    public String getOrganicTrackingToken() {
        return organicTrackingToken;
    }

    @JsonProperty("organic_tracking_token")
    public void setOrganicTrackingToken(String organicTrackingToken) {
        this.organicTrackingToken = organicTrackingToken;
    }

    @JsonProperty("carousel_media_count")
    public Integer getCarouselMediaCount() {
        return carouselMediaCount;
    }

    @JsonProperty("carousel_media_count")
    public void setCarouselMediaCount(Integer carouselMediaCount) {
        this.carouselMediaCount = carouselMediaCount;
    }

    @JsonProperty("carousel_media")
    public List<CarouselMedium> getCarouselMedia() {
        return carouselMedia;
    }

    @JsonProperty("carousel_media")
    public void setCarouselMedia(List<CarouselMedium> carouselMedia) {
        this.carouselMedia = carouselMedia;
    }

    @JsonProperty("location")
    public Location getLocation() {
        return location;
    }

    @JsonProperty("location")
    public void setLocation(Location location) {
        this.location = location;
    }

    @JsonProperty("lat")
    public Double getLat() {
        return lat;
    }

    @JsonProperty("lat")
    public void setLat(Double lat) {
        this.lat = lat;
    }

    @JsonProperty("lng")
    public Double getLng() {
        return lng;
    }

    @JsonProperty("lng")
    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "MediaResponse{" +
                "takenAt='" + takenAt + '\'' +
                ", pk='" + pk + '\'' +
                ", id='" + id + '\'' +
                ", deviceTimestamp='" + deviceTimestamp + '\'' +
                ", mediaType=" + mediaType +
                ", code='" + code + '\'' +
                ", clientCacheKey='" + clientCacheKey + '\'' +
                ", filterType=" + filterType +
                ", imageVersions2=" + imageVersions2 +
                ", originalWidth=" + originalWidth +
                ", originalHeight=" + originalHeight +
                ", user=" + user +
                ", canViewerReshare=" + canViewerReshare +
                ", captionIsEdited=" + captionIsEdited +
                ", commentLikesEnabled=" + commentLikesEnabled +
                ", commentThreadingEnabled=" + commentThreadingEnabled +
                ", hasMoreComments=" + hasMoreComments +
                ", maxNumVisiblePreviewComments=" + maxNumVisiblePreviewComments +
                ", canViewMorePreviewComments=" + canViewMorePreviewComments +
                ", commentCount=" + commentCount +
                ", inlineComposerDisplayCondition='" + inlineComposerDisplayCondition + '\'' +
                ", inlineComposerImpTriggerTime=" + inlineComposerImpTriggerTime +
                ", likeCount=" + likeCount +
                ", hasLiked=" + hasLiked +
                ", topLikers=" + topLikers +
                ", photoOfYou=" + photoOfYou +
                ", canSeeInsightsAsBrand=" + canSeeInsightsAsBrand +
                ", caption=" + caption +
                ", canViewerSave=" + canViewerSave +
                ", organicTrackingToken='" + organicTrackingToken + '\'' +
                ", carouselMediaCount=" + carouselMediaCount +
                ", carouselMedia=" + carouselMedia +
                ", location=" + location +
                ", lat=" + lat +
                ", lng=" + lng +
                ", viewCount=" + viewCount +
                '}';
    }
}
