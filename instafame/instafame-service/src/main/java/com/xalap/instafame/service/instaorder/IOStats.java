/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.instaorder;

import com.xalap.instafame.service.instaorder.task.IOTaskBean;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskStatus;
import com.xalap.instafame.service.instaorder.task.InstaOrderTaskType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 23/04/2019
 */
public class IOStats implements Serializable {

    private double charge;
    private final long createdCount;
    private final long inProgressCount;
    private final double createdTasksCost;
    private final Stat followers;
    private double followersCharge;
    private double likesCharge;
    private double commentsCharge;
    private double viewsCharge;
    private final Stat likes;
    private final Stat comments;
    private final Stat views;
    private final Stat realFollowers;
    private boolean delayWithFollowers;//Есть задержка при выполнении заказа на подписчиков, или нет
    private Date lastMediaTaskCreateTime;//Используется для определения, была пользователем добавлена публикация после этго времени или нет

    public IOStats(IOBean orderBean, List<IOTaskBean> beanList) {
        beanList.stream().filter(taskBean -> taskBean.getCharge() != null).forEach(taskBean -> {
            charge += taskBean.getCharge();
            if (taskBean.getTaskType() == InstaOrderTaskType.followers) {
                followersCharge += taskBean.getCharge();
            } else if (taskBean.getTaskType() == InstaOrderTaskType.likes) {
                likesCharge += taskBean.getCharge();
            } else if (taskBean.getTaskType() == InstaOrderTaskType.comments) {
                commentsCharge += taskBean.getCharge();
            } else if (taskBean.getTaskType() == InstaOrderTaskType.views) {
                viewsCharge += taskBean.getCharge();
            }
        });

        createdTasksCost = beanList.stream().filter(taskBean -> taskBean.getCharge() != null && taskBean.getStatus() == InstaOrderTaskStatus.Created)
                .mapToDouble(IOTaskBean::getCharge).sum();
        createdCount = beanList.stream()
                .filter(taskBean -> taskBean.getStatus() == InstaOrderTaskStatus.Created).count();
        //количество заданий в in progress
        inProgressCount = beanList.stream()
                .filter(taskBean -> taskBean.getStatus() == InstaOrderTaskStatus.InProgress).count();

        followers = countStats(InstaOrderTaskType.followers, orderBean, beanList);
        likes = countStats(InstaOrderTaskType.likes, orderBean, beanList);
        comments = countStats(InstaOrderTaskType.comments, orderBean, beanList);
        views = countStats(InstaOrderTaskType.views, orderBean, beanList);

        //Нужно для вычисления сколько подписчиков было у пользователя на момент старта раскрутки
        IOTaskBean firstFollowersTask = null;
        for (IOTaskBean bean : beanList) {
            if (bean.getTaskType() == InstaOrderTaskType.followers
                    //&& get.isPlanned()
                    && bean.getStartCount() != null) {
                if (firstFollowersTask == null) {
                    firstFollowersTask = bean;
                } else if (bean.getCreateTime().before(firstFollowersTask.getCreateTime())) {
                    firstFollowersTask = bean;
                }
                if (bean.getStatus() == InstaOrderTaskStatus.InProgress) {
                    if (bean.getCheatTask().isDelay(bean)) {
                        delayWithFollowers = true;
                    }
                }
            }
            if (bean.getTaskType() != InstaOrderTaskType.followers) {
                if (lastMediaTaskCreateTime == null || lastMediaTaskCreateTime.before(bean.getCreateTime())) {
                    lastMediaTaskCreateTime = bean.getCreateTime();
                }
            }
        }
        if (firstFollowersTask != null) {
            //Сколько сейчас подписчиков - сколько было в самом начале подписчиков
            int differenceFollowers = orderBean.getUser() != null ?
                    orderBean.getUser().getUserStats().getFollowedByCount() - firstFollowersTask.getStartCount() :
                    0;
            int planned = followers.getPlaned() - followers.getCurrent();
            //Сколько реально у него сейчас подписчиков
            realFollowers = new Stat(differenceFollowers, differenceFollowers + planned, orderBean.getFollowersCount());
        } else {
            realFollowers = new Stat(0, 0);
        }
    }

    public IOStats.Stat countStats(InstaOrderTaskType taskType, IOBean orderBean, List<IOTaskBean> tasks) {
        return countStats(taskType, taskType.orderCount(orderBean), tasks);
    }

    private IOStats.Stat countStats(InstaOrderTaskType taskType, int max, List<IOTaskBean> tasks) {
        int current = 0;
        int planned = 0;//Сколько мы получим единиц, если все created, ManualCheck и inProgress задачи будут выполнены
        for (IOTaskBean task : tasks) {
            if (task.getTaskType() == taskType) {

                //Намеренно оставляем дублирования, чтобы было более понятно
                if (task.getStatus() == InstaOrderTaskStatus.Completed || task.getStatus() == InstaOrderTaskStatus.InProgress ||
                        task.getStatus() == InstaOrderTaskStatus.Interrupted) {
                    current += task.quantityMinusRemains();
                }

                if (task.getStatus() == InstaOrderTaskStatus.InProgress || task.getStatus() == InstaOrderTaskStatus.Completed ||
                        task.getStatus() == InstaOrderTaskStatus.Created || task.getStatus() == InstaOrderTaskStatus.ManualCheck) {
                    planned += task.getQuantity();
                } else if (task.getStatus() == InstaOrderTaskStatus.Interrupted) {
                    planned += task.quantityMinusRemains();
                }
            }
        }
        return new IOStats.Stat(current, planned, max);
    }

    public boolean isDelayWithFollowers() {
        return delayWithFollowers;
    }

    public double getCreatedTasksCost() {
        return createdTasksCost;
    }

    public long getCreatedCount() {
        return createdCount;
    }

    public double getCharge() {
        return charge;
    }

    public long getInProgressCount() {
        return inProgressCount;
    }

    public Stat getFollowers() {
        return followers;
    }

    public Stat getLikes() {
        return likes;
    }

    public Stat getComments() {
        return comments;
    }

    public Stat getRealFollowers() {
        return realFollowers;
    }

    public double getFollowersCharge() {
        return followersCharge;
    }

    public double getRecoveryFollowersCharge() {
        return followers.getPlaned() > followers.getMax() ?
                (double) (followers.getPlaned() - followers.getMax())  //сколько подписчиков дали сверх нормы
                        / followers.getPlaned() //Всего дали
                        * followersCharge
                : 0;
    }

    public double getRecoveryFollowersChargePercent() {
        return followersCharge > 0 ? getRecoveryFollowersCharge() / followersCharge * 100 : 0;
    }

    public double getLikesCharge() {
        return likesCharge;
    }

    public double getCommentsCharge() {
        return commentsCharge;
    }

    public double getViewsCharge() {
        return viewsCharge;
    }

    public Stat getViews() {
        return views;
    }

    @Override
    public String toString() {
        return "InstaOrderStats{" +
                "charge=" + charge +
                ", inProgressCount=" + inProgressCount +
                ", followers=" + followers +
                ", likes=" + likes +
                ", comments=" + comments +
                '}';
    }

    public boolean needMoreLikesOrComments(IOBean instaOrderBean) {
        return (instaOrderBean.getLikesCount() != 0 && getLikes().currentPercent() < 100)
                || (instaOrderBean.getCommentsCount() != 0 && getComments().currentPercent() < 100);
    }

    /**
     * @return Может быть null, если задач вообще нет
     */
    public Date getLastMediaTaskCreateTime() {
        return lastMediaTaskCreateTime;
    }

    public static class Stat implements Serializable{
        private final int current;
        private final int planed;
        private final int max;

        public Stat(int current, int max) {
            this(current, current, max);
        }

        public Stat(int current, int planed, int max) {
            this.current = current;
            this.planed = planed;
            this.max = max;
        }

        String percent() {
            String value = "" + currentPercent();
            if (current != planed) {
                value += "/" + planed * 100 / max;
            }
            return value;
        }

        public int currentPercent() {
            return max != 0 ? current * 100 / max : 0;
        }

        public int plannedPercent() {
            return planed * 100 / max;
        }

        public String caption() {
            return max > 0 ? percent() + "% из " + max : "0";
        }

        public String captionFull() {
            return max > 0 ? percent() + "% (" + current +
                    (current != planed ? "/" + planed : "")
                    + "/" + max + ")" : "0";
        }

        public int getCurrent() {
            return current;
        }

        public int getMaxMinusCurrent() {
            return max - current;
        }

        public int getMaxMinusPlanned() {
            return max - planed;
        }

        public int getPlaned() {
            return planed;
        }

        public int getMax() {
            return max;
        }

        public boolean started() {
            return current > 0 || planed > 0;
        }

        @Override
        public String toString() {
            return "Stat{" +
                    "current=" + current +
                    ", planed=" + planed +
                    ", max=" + max +
                    '}';
        }
    }
}
