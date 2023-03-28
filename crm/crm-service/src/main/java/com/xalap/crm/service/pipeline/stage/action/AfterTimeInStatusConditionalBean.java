/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.pipeline.stage.action;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.concurrent.TimeUnit;

/**
 * @author Усов Андрей
 * @since 13/06/2019
 */
@Entity
@DiscriminatorValue("AfterTimeInStatus")
public class AfterTimeInStatusConditionalBean extends PipelineStageActionConditionBean {

    private long timeValue;
    private TimeUnit timeUnit;

    public long getTimeValue() {
        return timeValue;
    }

    public void setTimeValue(long timeValue) {
        this.timeValue = timeValue;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
