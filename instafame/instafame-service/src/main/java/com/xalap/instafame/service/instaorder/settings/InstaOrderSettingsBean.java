/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder.settings;

import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * @author Усов Андрей
 * @since 08/05/2019
 */
@Entity(name = InstaOrderSettingsBean.NAME)
public class InstaOrderSettingsBean implements IdHolder<Integer> {
    public static final String NAME = "IS$InstaOrderSettings";
    public static final String DEFAULT_LIKES = "defaultLikes";
    public static final String PREMIUM_FOLLOWERS = "premiumFollowers";
    public static final String ECONOMY_FOLLOWERS = "economyFollowers";
    public static final String REAL_FOLLOWERS = "realFollowers";
    public static final String CUSTOM_COMMENTS = "customComments";
    public static final String DEFAULT_VIEWS = "defaultViews";
    public static final String PREMIUM_LIKES = "premiumLikes";
    public static final String VIP_FOLLOWERS = "vipFollowers";

    @Id
    private Integer id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(DEFAULT_LIKES)
    private CheatTaskBean defaultLikesTask;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(DEFAULT_VIEWS)
    private CheatTaskBean defaultViewsTask;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(PREMIUM_FOLLOWERS)
    private CheatTaskBean premiumFollowersTask;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(ECONOMY_FOLLOWERS)
    private CheatTaskBean economyFollowersTask;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(VIP_FOLLOWERS)
    private CheatTaskBean vipFollowersTask;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(REAL_FOLLOWERS)
    private CheatTaskBean realFollowersTask;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(CUSTOM_COMMENTS)
    private CheatTaskBean customCommentsTask;
    @Caption("Максимальная скорость выдачи подписчиков")
    private boolean maximumFollowersSpeed;
    @Caption("Тип восстановления")
    private IORestoreType restoreType;
    @ColumnDefault("300")
    private int restoreMaxPercent;
    @Caption("Минимальное количество подписчиков для восстановления")
    @ColumnDefault("30")
    private int restoreMinFollowers;
    @ColumnDefault("110")
    private int restoreLevelPercent;
    @ColumnDefault("1500")
    private int vipLikesCount;
    @ColumnDefault("5")
    private int vipCommentsCount;
    @ColumnDefault("true")
    private boolean allowSendToInstagram;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CheatTaskBean getDefaultLikesTask() {
        return defaultLikesTask;
    }

    public void setDefaultLikesTask(CheatTaskBean defaultLikesTask) {
        this.defaultLikesTask = defaultLikesTask;
    }

    public CheatTaskBean getDefaultViewsTask() {
        return defaultViewsTask;
    }

    public void setDefaultViewsTask(CheatTaskBean defaultViewsTask) {
        this.defaultViewsTask = defaultViewsTask;
    }

    public CheatTaskBean getPremiumFollowersTask() {
        return premiumFollowersTask;
    }

    public void setPremiumFollowersTask(CheatTaskBean premiumFollowersTask) {
        this.premiumFollowersTask = premiumFollowersTask;
    }

    public CheatTaskBean getVipFollowersTask() {
        return vipFollowersTask;
    }

    public void setVipFollowersTask(CheatTaskBean vipFollowersTask) {
        this.vipFollowersTask = vipFollowersTask;
    }

    public CheatTaskBean getEconomyFollowersTask() {
        return economyFollowersTask;
    }

    public void setEconomyFollowersTask(CheatTaskBean economyFollowersTask) {
        this.economyFollowersTask = economyFollowersTask;
    }

    public CheatTaskBean getRealFollowersTask() {
        return realFollowersTask;
    }

    public void setRealFollowersTask(CheatTaskBean realFollowersTask) {
        this.realFollowersTask = realFollowersTask;
    }

    public CheatTaskBean getCustomCommentsTask() {
        return customCommentsTask;
    }

    public void setCustomCommentsTask(CheatTaskBean customCommentsTask) {
        this.customCommentsTask = customCommentsTask;
    }

    public boolean isMaximumFollowersSpeed() {
        return maximumFollowersSpeed;
    }

    public void setMaximumFollowersSpeed(boolean maximumFollowersSpeed) {
        this.maximumFollowersSpeed = maximumFollowersSpeed;
    }

    public int getRestoreMaxPercent() {
        return restoreMaxPercent;
    }

    public void setRestoreMaxPercent(int restoreMaxPercent) {
        this.restoreMaxPercent = restoreMaxPercent;
    }

    public int getRestoreLevelPercent() {
        return restoreLevelPercent;
    }

    public void setRestoreLevelPercent(int restoreLevelPercent) {
        this.restoreLevelPercent = restoreLevelPercent;
    }

    public int getVipLikesCount() {
        return vipLikesCount;
    }

    public void setVipLikesCount(int vipLikesCount) {
        this.vipLikesCount = vipLikesCount;
    }

    public int getVipCommentsCount() {
        return vipCommentsCount;
    }

    public void setVipCommentsCount(int vipCommentsCount) {
        this.vipCommentsCount = vipCommentsCount;
    }

    public boolean isAllowSendToInstagram() {
        return allowSendToInstagram;
    }

    public void setAllowSendToInstagram(boolean allowSendToInstagram) {
        this.allowSendToInstagram = allowSendToInstagram;
    }

    public int getRestoreMinFollowers() {
        return restoreMinFollowers;
    }

    public void setRestoreMinFollowers(int restoreMinFollowers) {
        this.restoreMinFollowers = restoreMinFollowers;
    }

    public IORestoreType getRestoreType() {
        return restoreType;
    }

    public void setRestoreType(IORestoreType restoreType) {
        this.restoreType = restoreType;
    }

    @Override
    public String toString() {
        return "InstaOrderSettingsBean{" +
                "id=" + id +
                ", defaultLikesTask=" + defaultLikesTask +
                ", defaultViewsTask=" + defaultViewsTask +
                ", premiumFollowersTask=" + premiumFollowersTask +
                ", economyFollowersTask=" + economyFollowersTask +
                ", realFollowersTask=" + realFollowersTask +
                ", customCommentsTask=" + customCommentsTask +
                ", maximumFollowersSpeed=" + maximumFollowersSpeed +
                ", restoreMaxPercent=" + restoreMaxPercent +
                ", restoreLevelPercent=" + restoreLevelPercent +
                ", vipLikesCount=" + vipLikesCount +
                ", vipCommentsCount=" + vipCommentsCount +
                ", allowSendToInstagram=" + allowSendToInstagram +
                '}';
    }
}
