/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instagram.direct.service.settings;

import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Усов Андрей
 * @since 28/01/2020
 */
@Entity()
@Table(name = InstagramDirectSettingsBean.NAME)
public class InstagramDirectSettingsBean implements IdHolder<Integer> {

    public static final String NAME = "I$DIRECTSETTINGS";

    @Id
    private Integer id;
    private @NotNull String lastActivityAt;
    @ColumnDefault("false")
    private boolean allowToSend;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastActivityAt() {
        return lastActivityAt;
    }

    public void setLastActivityAt(String lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public boolean isAllowToSend() {
        return allowToSend;
    }

    public void setAllowToSend(boolean allowToSend) {
        this.allowToSend = allowToSend;
    }

    @Override
    public String toString() {
        return "InstagramDirectBean{" +
                "id=" + id +
                ", lastActivityAt='" + lastActivityAt + '\'' +
                '}';
    }
}
