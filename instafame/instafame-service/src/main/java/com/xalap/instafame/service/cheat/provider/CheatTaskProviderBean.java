/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.instafame.service.cheat.provider;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdNameHolder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Усов Андрей
 * @since 14.05.2018
 */
@Entity(name = CheatTaskProviderBean.NAME)
public class CheatTaskProviderBean implements IdNameHolder<Integer> {

    public static final String NAME = "IS$CHEATTASKPROVIDER";
    public static final String ACTIVE = "active";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @NotNull
    @Column(unique = true)
    private String name;
    @NotNull
    private String apiUrl;
    @NotNull
    private String apiKey;
    @FieldName(ACTIVE)
    private boolean active;

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

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}

