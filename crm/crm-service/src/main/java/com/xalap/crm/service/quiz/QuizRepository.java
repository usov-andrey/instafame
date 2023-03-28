/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.quiz;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 10/05/2019
 */
@Repository
public interface QuizRepository extends PageableRepository<QuizBean, Integer> {

    @EntityGraph(attributePaths = {"lead"})
    @Query("select q from QuizBean q left join fetch q.lead l left join fetch l.contact c")
    List<QuizBean> findAll();

    QuizBean findByLead(LeadBean lead);
}
