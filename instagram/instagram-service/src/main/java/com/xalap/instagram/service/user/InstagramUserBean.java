/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.user;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolderWithName;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instagram.api.user.User;
import com.xalap.instagram.service.media.MediaBean;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity()
@Table(name = InstagramUserBean.NAME)
public class InstagramUserBean implements Serializable, IdHolderWithName<Integer> {
    public static final String NAME = "I$User";
    public static final String CREATE_TIME = "createTime";
    public static final String UPDATE_TIME = "updateTime";
    public static final String USER_NAME = "userName";
    public static final String FULL_NAME = "fullName";
    public static final String BIO = "bio";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + InstagramUserBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + InstagramUserBean.NAME,
            sequenceName = "SEQ_" + InstagramUserBean.NAME)
    private Integer id;
    @NotNull
    @FieldName(CREATE_TIME)
    private Date createTime;
    @FieldName(UPDATE_TIME)
    private Date updateTime;
    @NotNull
    private String internalId;
    @NotNull
    @FieldName(USER_NAME)
    private String userName;
    @Column
    @FieldName(FULL_NAME)
    private String fullName;
    @Column(length = 1000)
    @FieldName(BIO)
    private String biography;
    private boolean isPrivate;
    @Column(length = 4000)
    private String profilePicture;
    private UserStatsBean userStats;
    private UpdateFreq updateFreq;
    @Column(length = 4000)
    private String externalLink;
    /*
     * Время создания последней публикации
     */
    private Date lastMediaCreateTime;
    /**
     * Вермя последнего обновления списка публикаций у пользователя
     */
    private Date mediaListUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastMediaCreateTime() {
        return lastMediaCreateTime;
    }

    public void setLastMediaCreateTime(Date lastMediaCreateTime) {
        this.lastMediaCreateTime = lastMediaCreateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public UserStatsBean getUserStats() {
        return userStats;
    }

    public void setUserStats(UserStatsBean userStats) {
        this.userStats = userStats;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UpdateFreq getUpdateFreq() {
        return updateFreq;
    }

    public void setUpdateFreq(UpdateFreq updateFreq) {
        this.updateFreq = updateFreq;
    }

    public String getExternalLink() {
        return externalLink;
    }

    public void setExternalLink(String externalLink) {
        this.externalLink = externalLink;
    }

    public void update(User user) {
        internalId = user.getId();
        userName = user.getUserName();
        fullName = user.getFullName();
        biography = user.getBiography();
        isPrivate = user.getIsPrivate();
        profilePicture = user.getProfilePicture();
        if (userStats == null) {
            userStats = new UserStatsBean();
        }
        userStats.update(user);
        updateTime = new Date();
        externalLink = user.getExternalLink();
    }

    public String htmlBiography() {
        return StringHelper.replace(biography, "\n", "<br>");
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", internalId='" + internalId + '\'' +
                ", userName='" + userName + '\'' +
                ", isPrivate=" + isPrivate +
                ", userStats=" + userStats +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstagramUserBean userBean = (InstagramUserBean) o;

        return id.equals(userBean.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public float erMax(List<MediaBean> mediaBeanList) {
        float erMax = 0;
        for (MediaBean mediaBean : mediaBeanList) {
            float er = mediaBean.er(getUserStats().getFollowedByCount());
            if (er > erMax) {
                erMax = er;
            }
        }
        return erMax;
    }

    public String erMaxFormatted(List<MediaBean> mediaBeanList) {
        return String.format("%.4f", erMax(mediaBeanList));
    }

    public int likes(List<MediaBean> userMediaList) {
        return avg(userMediaList, mediaBean -> mediaBean.getStats().getLikesCount());
    }

    public int comments(List<MediaBean> userMediaList) {
        return avg(userMediaList, mediaBean -> mediaBean.getStats().getCommentsCount());
    }

    private int avg(List<MediaBean> userMediaList, Function<MediaBean, Integer> function) {
        //Считаем на основе последних 30 публикаций
        if (userMediaList.isEmpty()) {
            return 0;
        }
        userMediaList.sort((o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
        int count = 0;
        int counter = 0;
        for (MediaBean mediaBean : userMediaList) {
            count += function.apply(mediaBean);
            counter++;
            if (counter == 30) {
                break;
            }
        }
        return counter > 0 ? count / counter : 0;
    }

    public String accountUrl() {
        return "https://www.instagram.com/" + getUserName();
    }

    public Date updateTime() {
        return updateTime != null ? updateTime : createTime;
    }

    @Override
    public String getName() {
        return getUserName();
    }

    public Date getMediaListUpdateTime() {
        return mediaListUpdateTime;
    }

    public void setMediaListUpdateTime(Date lastMediaUpdateTime) {
        this.mediaListUpdateTime = lastMediaUpdateTime;
    }
}
