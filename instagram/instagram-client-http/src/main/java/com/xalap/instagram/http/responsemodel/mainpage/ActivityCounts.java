
/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.responsemodel.mainpage;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "comment_likes",
        "comments",
        "likes",
        "relationships",
        "usertags"
})
public class ActivityCounts {

    @JsonProperty("comment_likes")
    private Integer commentLikes;
    @JsonProperty("comments")
    private Integer comments;
    @JsonProperty("likes")
    private Integer likes;
    @JsonProperty("relationships")
    private Integer relationships;
    @JsonProperty("usertags")
    private Integer usertags;

    @JsonProperty("comment_likes")
    public Integer getCommentLikes() {
        return commentLikes;
    }

    @JsonProperty("comment_likes")
    public void setCommentLikes(Integer commentLikes) {
        this.commentLikes = commentLikes;
    }

    @JsonProperty("comments")
    public Integer getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(Integer comments) {
        this.comments = comments;
    }

    @JsonProperty("likes")
    public Integer getLikes() {
        return likes;
    }

    @JsonProperty("likes")
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @JsonProperty("relationships")
    public Integer getRelationships() {
        return relationships;
    }

    @JsonProperty("relationships")
    public void setRelationships(Integer relationships) {
        this.relationships = relationships;
    }

    @JsonProperty("usertags")
    public Integer getUsertags() {
        return usertags;
    }

    @JsonProperty("usertags")
    public void setUsertags(Integer usertags) {
        this.usertags = usertags;
    }

    @Override
    public String toString() {
        return "ActivityCounts{" +
                "commentLikes=" + commentLikes +
                ", comments=" + comments +
                ", likes=" + likes +
                ", relationships=" + relationships +
                ", usertags=" + usertags +
                '}';
    }
}
