/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.media.MediaType;
import com.xalap.instagram.http.responsemodel.media.Edge_;
import com.xalap.instagram.http.responsemodel.media.Edge__;
import com.xalap.instagram.http.responsemodel.media.ShortcodeMedia;
import com.xalap.instagram.http.responsemodel.media.SideCarEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 12.05.2018
 */
public class MediaWithLastActivity extends Media {

    private List<Comment> lastComments;
    private List<String> lastLikes;
    private String owner;

    public MediaWithLastActivity(ShortcodeMedia media) {
        fromShortCode(media);
        owner = media.getOwner().getUsername();
        lastComments = new ArrayList<>();
        if (media.getEdgeMediaToComment() != null) {
            for (Edge_ edge_ : media.getEdgeMediaToComment().getEdges()) {
                Comment comment = new Comment();
                comment.setTime(new Date(edge_.getNode().getCreatedAt() * 1000L));
                comment.setUserName(edge_.getNode().getOwner().getUsername());
                comment.setText(edge_.getNode().getText());
                lastComments.add(comment);
            }
        }
        lastLikes = new ArrayList<>();
        for (Edge__ edge__ : media.getEdgeMediaPreviewLike().getEdges()) {
            lastLikes.add(edge__.getNode().getUsername());
        }
    }

    private void fromShortCode(ShortcodeMedia media) {
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


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Comment> getLastComments() {
        return lastComments;
    }

    public void setLastComments(List<Comment> lastComments) {
        this.lastComments = lastComments;
    }

    public List<String> getLastLikes() {
        return lastLikes;
    }

    public void setLastLikes(List<String> lastLikes) {
        this.lastLikes = lastLikes;
    }

    @Override
    public String toString() {
        return "MediaWithLastActivity{" +
                "lastComments=" + lastComments +
                ", lastLikes=" + lastLikes +
                super.toString() + '}';
    }


}
