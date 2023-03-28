/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.quiz;

import com.xalap.crm.service.lead.LeadBean;
import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Усов Андрей
 * @since 10/05/2019
 */
@Service
public class QuizService extends CrudService<QuizBean, QuizRepository, Integer> {

    public QuizService() {
        super();
    }

    public Map<LeadBean, QuizBean> getQuizMap() {
        Map<LeadBean, QuizBean> result = new HashMap<>();
        for (QuizBean quizBean : getAll()) {
            result.put(quizBean.getLead(), quizBean);
        }
        return result;
    }

}
