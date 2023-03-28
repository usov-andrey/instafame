/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instagram.api.media.Media;
import com.xalap.instagram.api.media.MediaType;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity()
@Table(name = MediaBean.NAME)
public class MediaBean implements IdHolder<Integer> {
    public static final String NAME = "I$Media";
    public static final String CREATE_TIME = "createTime";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + MediaBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + MediaBean.NAME,
            sequenceName = "SEQ_" + MediaBean.NAME)
    private Integer id;
    @NotNull
    private String internalId;
    @NotNull
    private String code;
    @NotNull
    @Column(length = 4000)
    private String src;
    @Column(length = 4000)
    private String previewSrc;
    @NotNull
    private MediaType type;
    @Column(length = 5000)
    private String caption;
    @NotNull
    @FieldName(CREATE_TIME)
    private Date createTime;
    private MediaStatsBean stats;
    private Date updateTime;
    @NotNull
    private String owner;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstagramUserBean user;
    @Column(length = 5000)
    private String hashTags;
    @Column(length = 4000)
    private String videoSrc;
    private Boolean deleted;

    public MediaBean() {
    }

    public static void sortByDate(List<MediaBean> mediaBeanList) {
        Collections.sort(mediaBeanList, (o1, o2) -> o2.getCreateTime().compareTo(o1.getCreateTime()));
    }

    /**
     * Вначале идут те, у которых меньше всего нормализованных лайков
     */
    public static void sortByNormalizedLikes(List<MediaBean> mediaBeanList) {
        Collections.sort(mediaBeanList, (o1, o2) -> new Integer(o1.normalizedLikes()).compareTo(o2.normalizedLikes()));
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public MediaStatsBean getStats() {
        return stats;
    }

    public void setStats(MediaStatsBean stats) {
        this.stats = stats;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public InstagramUserBean getUser() {
        return user;
    }

    public void setUser(InstagramUserBean user) {
        this.user = user;
        setOwner(user.getUserName());
    }

    public String getVideoSrc() {
        return videoSrc;
    }

    public void setVideoSrc(String videoSrc) {
        this.videoSrc = videoSrc;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void update(Media media) {
        caption = media.getCaption();
        if (stats == null) {
            stats = new MediaStatsBean();
        }
        stats.update(media);
        updateTime = new Date();
        //Адрес зависит от кэшируемого сервера и может измениться
        setSrc(media.getSrc());
        setVideoSrc(media.getVideoUrl());
        setPreviewSrc(media.getThumbnailSrc());
        deleted = false;
    }

    public String getPreviewSrc() {
        return previewSrc;
    }

    public void setPreviewSrc(String previewSrc) {
        this.previewSrc = previewSrc;
    }

    @Override
    public String toString() {
        return "MediaBean{" +
                "id=" + id +
                ", internalId='" + internalId + '\'' +
                ", code='" + code + '\'' +
                ", src='" + src + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", stats=" + stats +
                ", updateTime=" + updateTime +
                ", userName='" + user.getUserName() + '\'' +
                '}';
    }

    public String getHashTags() {
        return hashTags;
    }

    public void setHashTags(String hashTags) {
        this.hashTags = hashTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaBean mediaBean = (MediaBean) o;

        return id.equals(mediaBean.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public void create(Media media, String owner) {
        setCode(media.getCode());
        setInternalId(media.getId());
        setType(media.getType());
        setSrc(media.getSrc());
        setVideoSrc(media.getVideoUrl());
        setPreviewSrc(media.getThumbnailSrc());
        setCreateTime(media.getCreateTime());
        setOwner(owner);
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String fileName() {
        return getId() + ".jpeg";
    }

    public float er(int followedByCount) {
        return ((float) getStats().er()) / followedByCount;
    }

    public String format(float value) {
        return String.format("%.4f", value);
    }

    public String type() {
        if (getType().equals(MediaType.video)) {
            return "V";
        } else if (getType().equals(MediaType.sideCar)) {
            return "G";
        }
        return "";
    }

    public String url() {
        return String.format("https://www.instagram.com/p/%s/", getCode());
    }

    /**
     * //Считаем, что 100% лайков пост набирает за 3 дня
     * За 24 часа он набирает 75% лайков
     * За 12 часов он набирает 50% лайков
     * За 6 часов он набирает 30% лайков
     * За 3 часа он набирает 20% лайков
     *
     * @return количество лайков приведенное к 100 процентам
     */
    public int normalizedLikes() {
        long minutesDiff = DateHelper.minutesBetweenDates(new Date(), getCreateTime());
        double coeff = 1d;
        if (minutesDiff <= 3 * 60) {
            coeff = 0.2d;
        } else if (minutesDiff <= 6 * 60) {
            coeff = 0.3d;
        } else if (minutesDiff <= 12 * 60) {
            coeff = 0.5d;
        } else if (minutesDiff <= 24 * 60) {
            coeff = 0.75d;
        }
        return new Double(getStats().getLikesCount() / coeff).intValue();
    }

}
