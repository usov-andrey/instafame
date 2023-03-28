
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "__typename",
        "id",
        "comments_disabled",
        "dimensions",
        "owner",
        "thumbnail_src",
        "is_video",
        "code",
        "date",
        "display_src",
        "caption",
        "comments",
        "likes"
})
public class UserMedia {

    @JsonProperty("__typename")
    private String typename;
    @JsonProperty("id")
    private String id;
    @JsonProperty("comments_disabled")
    private Boolean commentsDisabled;
    @JsonProperty("dimensions")
    private Dimensions dimensions;
    @JsonProperty("owner")
    private Owner owner;
    @JsonProperty("thumbnail_src")
    private String thumbnailSrc;
    @JsonProperty("is_video")
    private Boolean isVideo;
    @JsonProperty("code")
    private String code;
    @JsonProperty("date")
    private Integer date;
    @JsonProperty("display_src")
    private String displaySrc;
    @JsonProperty("caption")
    private String caption;
    @JsonProperty("comments")
    private Comments comments;
    @JsonProperty("likes")
    private Likes likes;
    @JsonProperty("createDate")
    private Date createdDate;
    @JsonProperty
    private List<String> taggedUsers;

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

    @JsonProperty("comments_disabled")
    public Boolean getCommentsDisabled() {
        return commentsDisabled;
    }

    @JsonProperty("comments_disabled")
    public void setCommentsDisabled(Boolean commentsDisabled) {
        this.commentsDisabled = commentsDisabled;
    }

    @JsonProperty("dimensions")
    public Dimensions getDimensions() {
        return dimensions;
    }

    @JsonProperty("dimensions")
    public void setDimensions(Dimensions dimensions) {
        this.dimensions = dimensions;
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

    @JsonProperty("is_video")
    public Boolean getIsVideo() {
        return isVideo;
    }

    @JsonProperty("is_video")
    public void setIsVideo(Boolean isVideo) {
        this.isVideo = isVideo;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("date")
    public Integer getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(Integer date) {
        this.date = date;
    }

    @JsonProperty("display_src")
    public String getDisplaySrc() {
        return displaySrc;
    }

    @JsonProperty("display_src")
    public void setDisplaySrc(String displaySrc) {
        this.displaySrc = displaySrc;
    }

    @JsonProperty("caption")
    public String getCaption() {
        return caption;
    }

    @JsonProperty("caption")
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String text() {
        return caption != null ? caption : "";
    }

    @JsonProperty("comments")
    public Comments getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Comments comments) {
        this.comments = comments;
    }

    @JsonProperty("likes")
    public Likes getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    @Override
    public String toString() {
        return this + "";
    }

    /**
     * Нельзя использовать этот метод, для получения даты
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date createTime() {
        return createdDate != null ? createdDate : new Date(date * 1000L);
    }

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserMedia userMedia = (UserMedia) o;

        return id.equals(userMedia.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String realId() {
        return id;//StringHelper.getStringBeforeNotEmpty(id, "_");
    }

    public void like() {
        getLikes().inc();
    }

}
