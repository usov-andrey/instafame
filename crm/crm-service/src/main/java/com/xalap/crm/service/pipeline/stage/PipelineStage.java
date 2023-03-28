/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage;

import com.xalap.crm.service.lead.LeadBean;

import java.util.function.Consumer;

/**
 * @author Усов Андрей
 * @since 16/06/2019
 */
public class PipelineStage {

    private final String code;
    private Consumer<LeadBean> consumer;

    public PipelineStage(String code) {
        this.code = code;
    }

    public PipelineStage(String code, Consumer<LeadBean> consumer) {
        this(code);
        this.consumer = consumer;
    }

    public PipelineStage setConsumer(Consumer<LeadBean> consumer) {
        this.consumer = consumer;
        return this;
    }

    public String getCode() {
        return code;
    }

    public void start(LeadBean bean) {
        if (consumer != null) {
            consumer.accept(bean);
        }
    }
}
