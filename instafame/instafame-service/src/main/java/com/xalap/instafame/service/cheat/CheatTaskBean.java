/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdNameHolder;
import com.xalap.framework.utils.DateHelper;
import com.xalap.framework.utils.StringHelper;
import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity(name = CheatTaskBean.NAME)
public class CheatTaskBean implements IdNameHolder<Integer> {

    public static final String NAME = "IS$CHEATTASK";
    public static final String NO = "no";
    public static final String TYPE = "type";
    public static final String DESC = "desc";
    public static final String PROVIDER = "provider";
    public static final String MIN = "min";
    public static final String MAX = "max";
    public static final String PRICE = "price";
    public static final String DISABLED = "disabled";
    public static final String COMMENT = "comment";
    public static final String RUSSIAN = "russian";
    public static final String CANCELABLE = "cancelable";
    public static final String RATING = "rating";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(NO)
    private int serviceNumber;
    @NotNull
    @FieldName(TYPE)
    private InstaOrderTaskType taskType;
    @NotNull
    private String name;
    @Column(length = 5000)
    @FieldName(DESC)
    private String description;
    @NotNull
    @FieldName(PROVIDER)
    private String providerName;
    @FieldName(MIN)
    private int min;
    @FieldName(MAX)
    private int max;
    @FieldName(PRICE)
    @ColumnDefault("0")
    private double costFor1000;
    private boolean speedSupport;
    @FieldName(DISABLED)
    private boolean disabled;
    private int maxDelayHours;
    @FieldName(COMMENT)
    private String comment;
    private boolean customComment;
    @ColumnDefault("true")
    private boolean active;
    @ColumnDefault("false")
    @FieldName(RUSSIAN)
    private boolean russian;
    @FieldName(RATING)
    @ColumnDefault("0")
    private int rating;
    @ColumnDefault("false")
    @FieldName(CANCELABLE)
    private boolean cancelable;
    @ColumnDefault("false")
    private boolean forPrivate;//Можно использовать для приватного аккаунта
    @ColumnDefault("false")
    private boolean notifyOnActive;//Уведомить, когда сервис станет снова активным
    @ColumnDefault("0")
    private int refillDays;//Сколько дней можно вызывать кнопку Refill для восстановления подписчиков
    @ColumnDefault("false")
    private boolean favorite;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(int serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public boolean isSpeedSupport() {
        return speedSupport;
    }

    public void setSpeedSupport(boolean speedSupport) {
        this.speedSupport = speedSupport;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean activeAndEnabled() {
        return active && !disabled;
    }

    public int getMaxDelayHours() {
        return maxDelayHours;
    }

    public void setMaxDelayHours(int maxDelayHours) {
        this.maxDelayHours = maxDelayHours;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public InstaOrderTaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(InstaOrderTaskType taskType) {
        this.taskType = taskType;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CheatTaskBean that = (CheatTaskBean) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isDelay(IOTaskBean bean) {
        return maxDelayHours > 0 && bean.getSendTime() != null && new Date().after(
                DateHelper.incHours(bean.getSendTime(), maxDelayHours));
    }

    public boolean isCustomComment() {
        return customComment;
    }

    public void setCustomComment(boolean customComment) {
        this.customComment = customComment;
    }

    public String taskName() {
        return getProviderName() + "." + getServiceNumber();
    }

    public double getCostFor1000() {
        return costFor1000;
    }

    public void setCostFor1000(double costFor1000) {
        this.costFor1000 = costFor1000;
    }

    public String caption() {
        return providerName + "." + serviceNumber + "." + name;
    }

    public String price() {
        return StringHelper.toString(costFor1000);
    }

    public boolean isRussian() {
        return russian;
    }

    public void setRussian(boolean russian) {
        this.russian = russian;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isForPrivate() {
        return forPrivate;
    }

    public void setForPrivate(boolean forPrivate) {
        this.forPrivate = forPrivate;
    }

    public boolean isNotifyOnActive() {
        return notifyOnActive;
    }

    public void setNotifyOnActive(boolean notifyOnActive) {
        this.notifyOnActive = notifyOnActive;
    }

    public int getRefillDays() {
        return refillDays;
    }

    public void setRefillDays(int refillDays) {
        this.refillDays = refillDays;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "CheatTaskBean{" +
                "id=" + id +
                ", serviceNumber=" + serviceNumber +
                ", taskType=" + taskType +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", providerName='" + providerName + '\'' +
                ", min=" + min +
                ", max=" + max +
                ", costFor1000=" + costFor1000 +
                ", speedSupport=" + speedSupport +
                ", disabled=" + disabled +
                ", maxDelayHours=" + maxDelayHours +
                ", comment='" + comment + '\'' +
                ", customComment=" + customComment +
                ", active=" + active +
                ", russian=" + russian +
                ", rating=" + rating +
                ", cancelable=" + cancelable +
                ", forPrivate=" + forPrivate +
                '}';
    }

    @Override
    public String name() {
        return providerName + "." + serviceNumber + "." + name;
    }
}

