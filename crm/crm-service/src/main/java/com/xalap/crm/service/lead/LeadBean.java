/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.contact.ContactDataHolder;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.domain.annotation.Caption;
import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdNameHolder;
import com.xalap.framework.utils.StringHelper;
import com.xalap.framework.utils.WebHelper;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.servlet.http.Cookie;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Entity()
@Table(name = LeadBean.NAME)
public class LeadBean implements IdNameHolder<Integer>, ContactDataHolder {

    public static final String NAME = "CRM$LEAD";
    public static final String CREATE_TIME = "createTime";
    public static final String NAME1 = "name";
    public static final String EMAIL = "email";
    public static final String INSTAGRAM = "instagram";
    public static final String CLIENT_ID = "clientId";
    public static final String SOURCE = "source";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @NotNull
    @FieldName(CREATE_TIME)
    private Date createTime = new Date();
    @NotNull
    @FieldName(NAME1)
    @Caption("Имя")
    private String name;
    @FieldName(EMAIL)
    private String email;
    @FieldName(INSTAGRAM)
    private String instagram;
    @FieldName(CLIENT_ID)
    private String clientId;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ContactBean contact;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PipelineStageBean stage;
    private Date stageTime;//Время попадания на текущий этап
    @FieldName(SOURCE)
    private String source;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String utmContent;
    private String utmTerm;
    @Column(length = 5000)
    private String referrer;
    @Column(length = 10000)
    private String cookies;
    //Пользователь или внешняя система может прислать один и тот же запрос несколько раз, поэтому полю мы идентифицируем
    //такие вот запросы
    private String requestId;

    public String getUtmSource() {
        return utmSource;
    }

    public void setUtmSource(String utmSource) {
        this.utmSource = utmSource;
    }

    public String getUtmMedium() {
        return utmMedium;
    }

    public void setUtmMedium(String utmMedium) {
        this.utmMedium = utmMedium;
    }

    public String getUtmCampaign() {
        return utmCampaign;
    }

    public void setUtmCampaign(String utmCampaign) {
        this.utmCampaign = utmCampaign;
    }

    public String getUtmContent() {
        return utmContent;
    }

    public void setUtmContent(String utmContent) {
        this.utmContent = utmContent;
    }

    public String getUtmTerm() {
        return utmTerm;
    }

    public void setUtmTerm(String utmTerm) {
        this.utmTerm = utmTerm;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public LeadBean setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getInstagram() {
        return instagram;
    }

    public LeadBean setInstagram(String instagram) {
        this.instagram = instagram;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ContactBean getContact() {
        return contact;
    }

    public void setContact(ContactBean contact) {
        this.contact = contact;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LeadBean leadBean = (LeadBean) o;

        return id.equals(leadBean.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public PipelineStageBean getStage() {
        return stage;
    }

    public void setStage(PipelineStageBean stage) {
        this.stage = stage;
    }

    public String utmCaption() {
        return utmSource != null ?
                (utmSource +
                        (utmTerm != null ? ":" + utmTerm : "")
                ) : "";
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        if (referrer != null && referrer.length() > 5000) {
            referrer = referrer.substring(0, 4999);
        }
        this.referrer = referrer;
    }

    public String cookie(String name) {
        String cookieSt = getCookies();
        if (StringHelper.isNotEmpty(cookieSt)) {
            List<Cookie> cookies = WebHelper.parseCookieString(cookieSt);
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    public String source() {
        return utmTerm;
    }

    public Date getStageTime() {
        return stageTime;
    }

    public void setStageTime(Date stageTime) {
        this.stageTime = stageTime;
    }

    public String caption() {
        return getName() + " email:" + getEmail() + " insta: https://www.instagram.com/" + getInstagram();
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Override
    public String toString() {
        return "LeadBean{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", instagram='" + instagram + '\'' +
                ", clientId='" + clientId + '\'' +
                ", contact=" + contact +
                ", stage=" + stage +
                ", stageTime=" + stageTime +
                ", source='" + source + '\'' +
                ", utmSource='" + utmSource + '\'' +
                ", utmMedium='" + utmMedium + '\'' +
                ", utmCampaign='" + utmCampaign + '\'' +
                ", utmContent='" + utmContent + '\'' +
                ", utmTerm='" + utmTerm + '\'' +
                ", referrer='" + referrer + '\'' +
                ", cookies='" + cookies + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}