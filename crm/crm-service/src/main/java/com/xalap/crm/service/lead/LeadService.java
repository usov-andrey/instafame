/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead;

import com.xalap.crm.service.lead.stage.LeadStageHistoryService;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageService;
import com.xalap.framework.data.CrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Service
public class LeadService extends CrudService<LeadBean, LeadRepository, Integer> {

    private static final Logger log = LoggerFactory.getLogger(LeadService.class);

    @Autowired
    private PipelineStageService stageService;
    @Autowired
    private LeadStageHistoryService statusHistoryService;

    public LeadService() {
        super();
    }

    public LeadBean getOrCreateLead(LeadBean lead) {
        LeadBean bean = repository().findByNameAndCreateTime(lead.getName(), lead.getCreateTime());
        if (bean == null) {
            lead.setId(null);
            bean = save(lead);
        } else {
            if (lead.getInstagram() != null && !lead.getInstagram().equals(bean.getInstagram())) {
                bean.setInstagram(lead.getInstagram());
                bean = save(bean);
            }

        }
        return bean;
    }

    @Override
    public LeadBean save(LeadBean bean) {
        if (bean.getId() != null) {

            LeadBean oldBean = getFromDB(bean.getId());

            bean = super.save(bean);
            //Изменяем историю старого статуса
            if (oldBean.getStage() != null && !oldBean.getStage().equals(bean.getStage())) {
                statusHistoryService.endStage(oldBean);
            }
            if (bean.getStage() != null && !bean.getStage().equals(oldBean.getStage())) {
                startStage(bean);
            }
        } else {
            bean = super.save(bean);
            if (bean.getStage() != null) {
                bean = startStage(bean);
            }
        }
        return bean;
    }

    private LeadBean startStage(LeadBean bean) {
        bean.setStageTime(new Date());
        bean = super.save(bean);
        statusHistoryService.startStage(bean);
        //pipelineService.startStage(bean);
        return bean;
    }

    public void changeStage(LeadBean lead, String stageCode) {
        PipelineStageBean stageBean = stageService.getByCode(stageCode);
        log.debug("Переводим заявку на этап " + stageCode + ": " + lead);
        lead.setStage(stageBean);
        save(lead);
    }

    /**
     * Перевод заявки из этапа source в target
     */
    public void changeStage(LeadBean lead, String sourceStageCode, String targetStageCode) {
        PipelineStageBean stageBean = stageService.getByCode(sourceStageCode);
        if (lead.getStage() != null && lead.getStage().equals(stageBean)) {
            changeStage(lead, targetStageCode);
        }
    }
}
