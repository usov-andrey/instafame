/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.crm.service.order.OrderBean;
import com.xalap.crm.service.order.orderline.OrderLineBean;
import com.xalap.crm.service.order.product.OrderProductBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.framework.utils.DateHelper;
import com.xalap.instafame.service.cheat.CheatTaskBean;
import com.xalap.instagram.service.api.InstagramClient;
import com.xalap.instagram.service.user.InstagramUserBean;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 06/01/2019
 * *
 */
@Entity()
@Table(name = IOBean.NAME)
public class IOBean implements IdHolder<Integer> {

    public static final String NAME = "IS$Order";

    public static final String CREATE_TIME = "createTime";
    public static final String STATUS = "status";
    public static final String ORDER = "order";
    public static final String SPEED = "speed";
    public static final String COMMENTS = "comments";
    public static final String LIKES = "likes";
    public static final String FOLLOWERS = "followers";
    public static final String PACKAGE = "package";
    public static final String USER = "user";
    public static final String VIEWS = "views";
    public static final String RESTORE_LEVEL = "restoreLevel";
    public static final String RESTORE_MAX = "restoreMax";
    public static final String FOLLOWERS_PER_DAY = "followersPerDay";
    public static final String STATUS_CHANGE_TIME = "statusChangeTime";
    public static final String RESTORE_START_TIME = "restoreStartTime";
    public static final String LAST_MEDIA_COUNT = "lastMediaCount";
    public static final String FOLLOWERS_TASK = "followersTask";
    public static final String REGION = "region";
    public static final String CITY = "city";
    public static final String ACCOUNT_TYPE = "accountType";
    public static final String MIN_AGE = "minAge";
    public static final String MAX_AGE = "maxAge";
    public static final String INSTAGRAM = "instagram";
    public static final String SEX = "sex";
    public static final String RESTORE_MIN_FOLLOWERS_FIELD = "restoreMinFollowersField";
    public static final String MEDIA_DIRECTION = "mediaDirection";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + IOBean.NAME)
    @SequenceGenerator(name = "sequence-generator" + IOBean.NAME,
            sequenceName = "SEQ_" + IOBean.NAME)
    private Integer id;

    @Caption("Время создания")
    @FieldName(CREATE_TIME)
    @NotNull
    private Date createTime;

    @Caption("Время начала восстановления")
    @FieldName(RESTORE_START_TIME)
    private Date restoreCreateTime;

    @Caption("Статус")
    @FieldName(STATUS)
    private @NotNull IOStatus status;

    @Caption("Время изменения статуса")
    @FieldName(STATUS_CHANGE_TIME)
    private Date statusChangeTime;

    @Caption("Пользователь")
    @FieldName(USER)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private InstagramUserBean user;

    @Caption("Пакет")
    @FieldName(PACKAGE)
    private @NotNull IOFollowersPackage followersPackage;

    @Caption("Подписчики")
    @FieldName(FOLLOWERS)
    private int followersCount;

    @Caption("Лайки")
    @FieldName(LIKES)
    private int likesCount;

    @FieldName(COMMENTS)
    @Caption("Комментарии")
    private int commentsCount;

    @FieldName(VIEWS)
    @Caption("Просмотры")
    @ColumnDefault("0")
    private int viewsCount;

    @Caption("Скорость")
    @FieldName(SPEED)
    private IOFollowersSpeed orderStrategy;

    @Caption("Заказ")
    @FieldName(ORDER)
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderBean order;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderLineBean orderLine;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private OrderProductBean orderProduct;

    @Caption("Максимальный процент восстановления подписчиков")
    @FieldName(RESTORE_MAX)
    private Integer restoreMaxPercent;

    @Caption("Уровень до которого восстанавливать подписчиков")
    @FieldName(RESTORE_LEVEL)
    private Integer restoreLevelPercent;

    @Caption("Минимальное количество подписчиков для восстановления")
    @FieldName(RESTORE_MIN_FOLLOWERS_FIELD)
    private Integer restoreMinFollowers;

    @Caption("Подписчиков в день")
    @FieldName(FOLLOWERS_PER_DAY)
    private Integer followersPerDay;

    @Caption("Количество последних публикаций")
    @FieldName(LAST_MEDIA_COUNT)
    private Integer lastMediaCount;

    @FieldName(MEDIA_DIRECTION)
    private IOMediaDirection mediaDirection;

    private IOType ioType;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @FieldName(FOLLOWERS_TASK)
    @Caption("Сервис набора подписчиков")
    private CheatTaskBean followersTask;

    //Параметры аккаунтов
    @Caption("Регион")
    @FieldName(REGION)
    private IORegion region;

    @Caption("Город")
    @FieldName(CITY)
    private IOCity city;

    @Caption("Тип аккаунтов")
    @FieldName(ACCOUNT_TYPE)
    private IOAccountType accountType;

    @Caption("Мин возраст")
    @FieldName(MIN_AGE)
    private Integer minAge;
    @Caption("Макс возраст")
    @FieldName(MAX_AGE)
    private Integer maxAge;

    @Caption("Пол")
    @FieldName(SEX)
    private IOSex sex;
    @Caption("Инстаграм")
    @FieldName(INSTAGRAM)
    private String instagram;

    public IOFollowersPackage getFollowersPackage() {
        return followersPackage;
    }

    public void setFollowersPackage(IOFollowersPackage followersPackage) {
        this.followersPackage = followersPackage;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
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

    public Date getStatusChangeTime() {
        return statusChangeTime;
    }

    public void setStatusChangeTime(Date statusChangeTime) {
        this.statusChangeTime = statusChangeTime;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public IOFollowersSpeed getOrderStrategy() {
        return orderStrategy;
    }

    public void setOrderStrategy(IOFollowersSpeed orderStrategy) {
        this.orderStrategy = orderStrategy;
    }

    public InstagramUserBean getUser() {
        return user;
    }

    public void setUser(InstagramUserBean user) {
        this.user = user;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public IOStatus getStatus() {
        return status;
    }

    public void setStatus(IOStatus status) {
        this.status = status;
    }

    public IOStats.Stat progressHours() {
        return getOrderStrategy() != null ? getOrderStrategy().progressHours(this) : new IOStats.Stat(0, 0);
    }

    public IOMediaDirection getMediaDirection() {
        return mediaDirection;
    }

    public void setMediaDirection(IOMediaDirection mediaDirection) {
        this.mediaDirection = mediaDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IOBean that = (IOBean) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public IOStats.Stat refillingHours() {
        return new IOStats.Stat(Long.valueOf(DateHelper.daysBetweenDates(restoreCreateTime(), new Date())).intValue(), 30);
    }

    private Date restoreCreateTime() {
        return getRestoreCreateTime() != null ? getRestoreCreateTime() :
                DateHelper.incDays(getCreateTime(), daysForFollowers());
    }

    /**
     * @return сколько дней необходимо, чтобы выполнить заказ на подписчиков с заданной в заказе скоростью
     */
    private int daysForFollowers() {
        return getFollowersCount() / getOrderStrategy().followersPerHour(this) / 24;
    }

    public String statusCaption() {
        return getStatus() == IOStatus.InProgress ?
                "PR:" + progressHours().caption()
                :
                (getStatus() == IOStatus.Refilling ?
                        (restoreMaxPercent != null && restoreMaxPercent <= 100 ? "RE(STOP):" : "RE:")
                                + refillingHours().caption()
                        : getStatus().name());
    }

    @Override
    public String toString() {
        return "IOBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", restoreCreateTime=" + restoreCreateTime +
                ", status=" + status +
                ", statusChangeTime=" + statusChangeTime +
                ", user=" + user +
                ", followersPackage=" + followersPackage +
                ", followersCount=" + followersCount +
                ", likesCount=" + likesCount +
                ", commentsCount=" + commentsCount +
                ", viewsCount=" + viewsCount +
                ", orderStrategy=" + orderStrategy +
                ", order=" + order +
                ", orderLine=" + orderLine +
                ", orderProduct=" + orderProduct +
                ", restoreMaxPercent=" + restoreMaxPercent +
                ", restoreLevelPercent=" + restoreLevelPercent +
                ", restoreMinFollowers=" + restoreMinFollowers +
                ", followersPerDay=" + followersPerDay +
                ", lastMediaCount=" + lastMediaCount +
                ", mediaDirection=" + mediaDirection +
                ", ioType=" + ioType +
                ", followersTask=" + followersTask +
                ", region=" + region +
                ", city=" + city +
                ", accountType=" + accountType +
                ", minAge=" + minAge +
                ", maxAge=" + maxAge +
                ", sex=" + sex +
                ", instagram='" + instagram + '\'' +
                '}';
    }

    public boolean progressMoreStat(IOStats.Stat stat) {
        return progressHours().currentPercent() * 1.15 > stat.plannedPercent();
    }

    public int getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Integer getRestoreMaxPercent() {
        return restoreMaxPercent;
    }

    public void setRestoreMaxPercent(Integer restoreMaxPercent) {
        this.restoreMaxPercent = restoreMaxPercent;
    }

    public Integer getRestoreLevelPercent() {
        return restoreLevelPercent;
    }

    public void setRestoreLevelPercent(Integer restoreLevelPercent) {
        this.restoreLevelPercent = restoreLevelPercent;
    }

    public Integer getFollowersPerDay() {
        return followersPerDay;
    }

    public void setFollowersPerDay(Integer followersPerDay) {
        this.followersPerDay = followersPerDay;
    }

    public Date getRestoreCreateTime() {
        return restoreCreateTime;
    }

    public void setRestoreCreateTime(Date restoreCreateTime) {
        this.restoreCreateTime = restoreCreateTime;
    }

    public OrderLineBean getOrderLine() {
        return orderLine;
    }

    public void setOrderLine(OrderLineBean orderLine) {
        this.orderLine = orderLine;
    }

    public IOType getIoType() {
        return ioType;
    }

    public void setIoType(IOType ioType) {
        this.ioType = ioType;
    }

    public Integer getLastMediaCount() {
        return lastMediaCount;
    }

    public void setLastMediaCount(Integer lastMediaCount) {
        this.lastMediaCount = lastMediaCount;
    }

    public CheatTaskBean getFollowersTask() {
        return followersTask;
    }

    public void setFollowersTask(CheatTaskBean followersTask) {
        this.followersTask = followersTask;
    }

    public IORegion getRegion() {
        return region;
    }

    public void setRegion(IORegion region) {
        this.region = region;
    }

    public IOCity getCity() {
        return city;
    }

    public void setCity(IOCity city) {
        this.city = city;
    }

    public IOAccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(IOAccountType accountType) {
        this.accountType = accountType;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public IOSex getSex() {
        return sex;
    }

    public void setSex(IOSex sex) {
        this.sex = sex;
    }

    public OrderProductBean getOrderProduct() {
        return orderProduct;
    }

    public IOBean setOrderProduct(OrderProductBean orderProduct) {
        this.orderProduct = orderProduct;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public IOBean setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public Integer getRestoreMinFollowers() {
        return restoreMinFollowers;
    }

    public void setRestoreMinFollowers(Integer restoreMinFollowers) {
        this.restoreMinFollowers = restoreMinFollowers;
    }

    public String accountUrl() {
        return InstagramClient.accountURL(instagram);
    }
}
