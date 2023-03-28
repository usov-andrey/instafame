/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.lead;

import com.xalap.crm.service.contact.ContactBean;
import com.xalap.crm.service.pipeline.stage.PipelineStageBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Repository
public interface LeadRepository extends PageableRepository<LeadBean, Integer> {

    String QUERY = "select l from LeadBean l left join fetch l.contact c left join fetch l.stage s left join fetch s.pipeline p";

    @Query(QUERY)
    List<LeadBean> findAll();

    LeadBean findByNameAndCreateTime(String name, Date createTime);

    @Query(QUERY + " where l.stage = :stage")
    List<LeadBean> findByStage(@Param("stage") PipelineStageBean stage);

    List<LeadBean> findByContact(ContactBean contact);

    Optional<LeadBean> findByRequestId(String requestId);
}
