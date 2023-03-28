/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.http.model;

import com.xalap.instagram.http.responsemodel.Story;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Усов Андрей
 * @since 29.04.17
 */
public class Feed {

    private List<LikeData> likes = new ArrayList<>();
    private List<CommentData> comments = new ArrayList<>();
    private List<FollowedByData> followedBy = new ArrayList<>();

    public Feed() {
    }

    public List<LikeData> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeData> likes) {
        this.likes = likes;
    }

    public List<CommentData> getComments() {
        return comments;
    }

    public void setComments(List<CommentData> comments) {
        this.comments = comments;
    }

    public List<FollowedByData> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<FollowedByData> followedBy) {
        this.followedBy = followedBy;
    }

    public void addStory(Story story, Consumer<String> followConsumer, Consumer<String> likeConsumer) {
        int type = story.getType();
        if (type == 1) {
            //like
            likes.add(new LikeData(story.mediaActivity()));
            likeConsumer.accept(story.getUser().getUsername());
        } else if (type == 3) {
            //follow
            followedBy.add(new FollowedByData(new UserActivity(story.getUser().getUsername())));
            followConsumer.accept(story.getUser().getUsername());
        } else if (type == 2) {
            //комментарий
            comments.add(new CommentData(story.mediaActivity()));
        } else if (type == 19 || type == 5 || type == 12 || type == 8) {
            //Добавление друга из facebook(19) или repost(5) или меня отметили(12), ничего не делаем
            //type=8 - упоминание
        } else {
            throw new IllegalStateException("Not found type:" + type + " in story " + story);
        }
    }

    /**
     * Люди, которые проявили активность в моей ленте
     */
    public List<String> users() {
        LinkedHashSet<String> users = likes.stream().map(like -> like.getObject().getUserName()).collect(Collectors.toCollection(LinkedHashSet::new));
        users.addAll(comments.stream().map(comment -> comment.getObject().getUserName()).collect(Collectors.toList()));
        users.addAll(followedBy.stream().map(followedByData -> followedByData.getObject().getUserName()).collect(Collectors.toList()));
        return new ArrayList<>(users);
    }

    public boolean containUser(String userName) {
        return containUser(userName, likes) || containUser(userName, comments);
    }

    private <T extends Data<MediaActivity>> boolean containUser(String userName, List<T> objects) {
        for (T object : objects) {
            if (object.getObject().getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }

}
