/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.api.user;

import com.xalap.instagram.api.media.Media;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Информация о пользователе
 *
 * @author Усов Андрей
 * @since 14.03.2018
 */
public class User {

    private String id;
    private String userName;
    private String fullName;
    private String biography;
    private int followsCount;
    private int followedByCount;
    private int mediaCount;
    private List<Media> lastMedias;
    private boolean isPrivate;
    private String profilePicture;
    private String externalLink;

    //Вычислимые поля
    private Integer likeRatio;
    private Integer commentRatio;

    public User() {
    }
/*
    public User(User_ user) {
        id = user.getPk();
        userName = user.getUsername();
        if (user.getFullName() != null) {
            fullName = user.getFullName();
        }
        if (user.getFollowerCount() != null) {
            followedByCount = user.getFollowerCount();
        }
        isPrivate = user.getIsPrivate();
        profilePicture = user.getProfilePicUrl();
    }*/

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public int getFollowsCount() {
        return followsCount;
    }

    public void setFollowsCount(int followsCount) {
        this.followsCount = followsCount;
    }

    public int getFollowedByCount() {
        return followedByCount;
    }

    public void setFollowedByCount(int followedByCount) {
        this.followedByCount = followedByCount;
    }

    public int getMediaCount() {
        return mediaCount;
    }

    public void setMediaCount(int mediaCount) {
        this.mediaCount = mediaCount;
    }

    public List<Media> getLastMedias() {
        return lastMedias;
    }

    public void setLastMedias(List<Media> lastMedias) {
        this.lastMedias = lastMedias;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    private int countRatio(Function<Media, Integer> countFunction) {
        int counter = 0;
        int count = lastMedias.size();
        if (count == 0) {
            return count;
        }
        for (Media userMedia : lastMedias) {
            counter += countFunction.apply(userMedia);
        }
        return counter / count;
    }

    public int count(Function<Media, Integer> countFunction) {
        int counter = 0;
        for (Media userMedia : lastMedias) {
            counter += countFunction.apply(userMedia);
        }
        return counter;
    }

    public Integer likeRatio() {
        if (likeRatio == null) {
            likeRatio = countRatio(Media::getLikesCount);
        }
        return likeRatio;
    }

    public Integer commentRatio() {
        if (commentRatio == null) {
            commentRatio = countRatio(Media::getCommentsCount);
        }
        return commentRatio;
    }

    public Integer rating() {
        return likeRatio() + commentRatio() * 2;
    }


    /**
     * Количество подписчиков поделенное на количество подписок
     */
    public Double followRating() {
        if (followsCount == 0) {
            return 0D;
        }
        return followedByCount * 1D / followsCount;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", followsCount=" + followsCount +
                ", followedByCount=" + followedByCount +
                ", mediaCount=" + mediaCount +
                ", lastMedias=" + lastMedias +
                ", isPrivate=" + isPrivate +
                ", profilePicture='" + profilePicture + '\'' +
                ", externalLink='" + externalLink + '\'' +
                ", likeRatio=" + likeRatio +
                ", commentRatio=" + commentRatio +
                '}';
    }
/*
    public String lastPostTimeString() {
        Date lastPostTime = lastPostTime();
        return lastPostTime == null ? "" : DateHelper.getDateTime(lastPostTime);
    }*/

    public Date lastPostTime() {
        return mediaCount == 0 ? null :
                lastMedias.size() == 0 ?
                        null
                        : lastMedias.get(0).getCreateTime();
    }

    public boolean havePostAfterTime(Date time) {
        Date lastPostTime = lastPostTime();
        return !isPrivate && lastPostTime != null && lastPostTime.after(time);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userName.equals(user.userName);

    }

    @Override
    public int hashCode() {
        return userName.hashCode();
    }
/*
    public List<String> biographyWords() {
        if (StringHelper.isEmpty(getBiography())) {
            return Collections.emptyList();
        }
        return Arrays.asList(getBiography().split("\\p{P}?[ \\t\\n\\r/]+"));
    }

    public boolean biographyIntersects(Collection<String> keywords) {
        return CollectionHelper.intersect(StringHelper.lowerCaseList(keywords), StringHelper.lowerCaseList(biographyWords()));
    }*/

    public boolean getIsPrivate() {
        return isPrivate;
    }
}
