/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.contact;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 14/06/2019
 */
@Entity()
@Table(name = ContactDataBean.NAME)
public class ContactDataBean extends ContactData implements IdHolder<Integer>, Serializable {

    private static final long serialVersionUID = 1905122041950251257L;

    public static final String NAME = "CRM$CONTACTDATA";
    public static final String CREATE_TIME = "createTime";
    public static final String EXPIRE_DATE = "expireDate";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;

    @FieldName(CREATE_TIME)
    private @NotNull Date createTime;

    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContactBean contact;
    @FieldName(EXPIRE_DATE)
    private Date expireDate;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "ContactDataBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", value='" + getValue() + '\'' +
                ", dataType=" + getDataType() +
                ", contact=" + contact +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactDataBean dataBean = (ContactDataBean) o;

        return id.equals(dataBean.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

}
