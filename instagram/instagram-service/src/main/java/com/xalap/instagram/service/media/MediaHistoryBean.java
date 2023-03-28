/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.service.media;

import com.xalap.instagram.api.media.Media;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity(name = MediaHistoryBean.NAME)
public class MediaHistoryBean {
    public static final String NAME = "I$MediaHistory";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + MediaHistoryBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + MediaHistoryBean.NAME,
            sequenceName = "SEQ_" + MediaHistoryBean.NAME)
    private Integer id;
    private Date createTime;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private MediaBean media;
    private MediaStatsBean stats;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MediaBean getMedia() {
        return media;
    }

    public void setMedia(MediaBean media) {
        this.media = media;
    }

    public MediaStatsBean getStats() {
        return stats;
    }

    public void setStats(MediaStatsBean stats) {
        this.stats = stats;
    }

    public void update(Media media) {
        if (stats == null) {
            stats = new MediaStatsBean();
        }
        stats.update(media);
    }

    @Override
    public String toString() {
        return "MediaHistoryBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", media=" + media +
                ", stats=" + stats +
                '}';
    }
}
