/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.domain.holder.IdNameHolder;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Контакт
 *
 * @author Усов Андрей
 * @since 22/02/2019
 */
@Entity()
@Table(name = ContactBean.TABLE_NAME)
public class ContactBean implements Serializable, IdNameHolder<Integer> {
    private static final long serialVersionUID = 1905122041950251207L;

    public static final String TABLE_NAME = "CRM$CONTACT";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + TABLE_NAME)
    @SequenceGenerator(name = "sequence-generator" + TABLE_NAME,
            sequenceName = "SEQ_" + TABLE_NAME)
    private Integer id;
    @NotNull
    private Date createTime;
    @NotNull
    private String name;
    /**
     * Готов контакт получать информацию о промо или нет
     */
    @ColumnDefault("false")
    private boolean canSendPromo;

    public ContactBean() {
    }

    public ContactBean(@NotNull String name) {
        this.name = name;
        createTime = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public ContactBean setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCanSendPromo() {
        return canSendPromo;
    }

    public void setCanSendPromo(boolean canSendPromo) {
        this.canSendPromo = canSendPromo;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactBean that = (ContactBean) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}