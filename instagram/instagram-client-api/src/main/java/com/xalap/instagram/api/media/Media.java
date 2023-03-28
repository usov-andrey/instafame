/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api.media;

import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.03.2018
 */
public class Media {

    protected String id;
    protected String src;
    protected String code;
    protected MediaType type;
    protected String caption;
    protected int likesCount;
    protected int commentsCount;
    protected Date createTime;
    protected String thumbnailSrc;
    protected String videoUrl;
    protected List<String> sideCarUrls;
    protected Integer viewViewCount;

    /*
    public Media(ShortcodeMedia media) {
        try {
            id = media.getId();
            code = media.getShortcode();
            type = MediaType.value(media.getTypename());
            videoUrl = media.getVideoUrl();
            src = media.getDisplayUrl();
            caption = media.getEdgeMediaToCaption().getEdges().isEmpty() ? "" : media.getEdgeMediaToCaption().getEdges().get(0).getNode().getText();
            likesCount = media.getEdgeMediaPreviewLike().getCount();
            commentsCount = media.getEdgeMediaToComment() != null ? media.getEdgeMediaToComment().getCount() : 0;//TODO
            createTime = new Date(media.getTakenAtTimestamp() * 1000L);
            Collections.sort(media.getDisplayResources(), (o1, o2) -> o1.getConfigWidth().compareTo(o2.getConfigWidth()));
            media.getDisplayResources().stream().findFirst().ifPresent(resource -> thumbnailSrc = resource.getSrc());
            if (type.equals(MediaType.sideCar)) {
                sideCarUrls = new ArrayList<>();
                for (SideCarEdge sideCarEdge : media.getEdgeSidecarToChildren().getEdges()) {
                    ShortcodeMedia node = sideCarEdge.getNode();
                    sideCarUrls.add(MediaType.value(node.getTypename()).equals(MediaType.video) ? node.getVideoUrl() : node.getDisplayUrl());
                }
            }
            viewViewCount = media.getViewViewCount();
        } catch (Exception e) {
            throw new IllegalStateException("Media:" + media, e);
        }
    }

    public Media(com.xalap.instagram.http.responsemodel.profile.Node media) {
        id = media.getId();
        src = media.getDisplayUrl();
        code = media.getShortcode();
        type = media.getIsVideo() ? MediaType.video : MediaType.image;
        videoUrl = media.getVideoUrl();
        caption = media.getEdgeMediaToCaption().getEdges().isEmpty() ? "" : media.getEdgeMediaToCaption().getEdges().get(0).getNode().getText();
        likesCount = media.getEdgeLikedBy().getCount();
        commentsCount = media.getEdgeMediaToComment().getCount();
        createTime = new Date(media.getTakenAtTimestamp() * 1000L);
        Collections.sort(media.getThumbnailResources(), (o1, o2) -> o1.getConfigWidth().compareTo(o2.getConfigWidth()));
        media.getThumbnailResources().stream().findFirst().ifPresent(resource -> thumbnailSrc = resource.getSrc());
    }
    */

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void like() {
        likesCount++;
    }

    public String getThumbnailSrc() {
        return thumbnailSrc;
    }

    public void setThumbnailSrc(String thumbnailSrc) {
        this.thumbnailSrc = thumbnailSrc;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getSideCarUrls() {
        return sideCarUrls;
    }

    public void setSideCarUrls(List<String> sideCarUrls) {
        this.sideCarUrls = sideCarUrls;
    }

    public Integer getViewViewCount() {
        return viewViewCount;
    }

    public void setViewViewCount(Integer viewViewCount) {
        this.viewViewCount = viewViewCount;
    }

    @Override
    public String toString() {
        return "Media{" +
                "id='" + id + '\'' +
                ", src='" + src + '\'' +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", createTime=" + createTime +
                '}';
    }
}
