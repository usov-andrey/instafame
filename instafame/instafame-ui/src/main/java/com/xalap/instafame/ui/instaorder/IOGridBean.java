/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.ui.instaorder;

import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.instafame.service.instaorder.IOBean;
import com.xalap.instafame.service.instaorder.IOStats;
import com.xalap.instagram.service.user.InstagramUserBean;

/**
 * @author Усов Андрей
 * @since 11/06/2019
 */
public class IOGridBean implements IdHolder<Integer> {

    private IOBean instaOrderBean;
    private IOStats stats;
    private long sameInstagramOrderCount;

    public IOGridBean(IOBean instaOrderBean) {
        this.instaOrderBean = instaOrderBean;
    }

    public IOBean getInstaOrderBean() {
        return instaOrderBean;
    }

    public void setInstaOrderBean(IOBean instaOrderBean) {
        this.instaOrderBean = instaOrderBean;
    }

    public IOStats getStats() {
        return stats;
    }

    public void setStats(IOStats stats) {
        this.stats = stats;
    }

    @Override
    public Integer getId() {
        return instaOrderBean.getId();
    }

    @Override
    public void setId(Integer id) {
        instaOrderBean.setId(id);
    }

    public InstagramUserBean getUser() {
        return instaOrderBean.getUser();
    }

    public double profit() {
        return getInstaOrderBean().getOrder().getRevenue() != null ?
                getInstaOrderBean().getOrder().getRevenue() - getStats().getCharge() : 0;
    }

    public long getSameInstagramOrderCount() {
        return sameInstagramOrderCount;
    }

    public void setSameInstagramOrderCount(long sameInstagramOrderCount) {
        this.sameInstagramOrderCount = sameInstagramOrderCount;
    }
}
