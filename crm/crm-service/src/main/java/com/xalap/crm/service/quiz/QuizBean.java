/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.quiz;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 02/05/2019
 */
@Entity()
@Table(name = QuizBean.NAME)
public class QuizBean implements IdHolder<Integer> {

    public static final String NAME = "CRM$QUIZ";
    public static final String SEX = "sex";
    public static final String PRICE1000 = "price1000";
    public static final String EXPERIENCE = "experience";
    public static final String GIFT_TYPE = "giftType";
    public static final String STRATEGY = "strategy";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LeadBean lead;
    @FieldName(SEX)
    private String sex;
    @FieldName(PRICE1000)
    private String price1000;
    @FieldName(EXPERIENCE)
    private String experience;
    @FieldName(GIFT_TYPE)
    private String giftType;
    @FieldName(STRATEGY)
    private String strategy;
    private Date bonusSendTime;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPrice1000() {
        return price1000;
    }

    public void setPrice1000(String price1000) {
        this.price1000 = price1000;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getGiftType() {
        return giftType;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public LeadBean getLead() {
        return lead;
    }

    public void setLead(LeadBean lead) {
        this.lead = lead;
    }

    public Date getBonusSendTime() {
        return bonusSendTime;
    }

    public void setBonusSendTime(Date bonusSendTime) {
        this.bonusSendTime = bonusSendTime;
    }

    @Override
    public String toString() {
        return "QuizBean{" +
                "id=" + id +
                ", lead=" + lead +
                ", sex='" + sex + '\'' +
                ", price1000='" + price1000 + '\'' +
                ", experience='" + experience + '\'' +
                ", giftType='" + giftType + '\'' +
                ", strategy='" + strategy + '\'' +
                '}';
    }
}