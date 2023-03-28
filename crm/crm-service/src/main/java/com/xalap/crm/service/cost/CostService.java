/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.cost;

import com.xalap.framework.data.CrudService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Затраты
 *
 * @author Усов Андрей
 * @since 16/01/2019
 */
@Service
public class CostService extends CrudService<CostBean, CostRepository, Integer> {

    public CostService() {
        super();
    }

    /**
     * Импорт данных о затратах в базу
     *
     * @param beans - затраты без указания идентификаторов
     */
    public void importCost(List<CostBean> beans) {
        //Ищем каждую затрату по типу, времени в базе
        //Если находим и значение затраты отличается, то обновляем в базе
        //Если не находим, то добавляем
        for (CostBean bean : beans) {
            Optional<CostBean> optionalCostBean = getRepository().findByCostTypeAndCostTime(bean.getCostType(), bean.getCostTime());
            if (optionalCostBean.isPresent()) {
                CostBean costBean = optionalCostBean.get();
                if (costBean.getCost().equals(bean.getCost())) {
                    //Затраты совпадают, ничего не делаем
                    continue;
                }
                bean.setId(costBean.getId());
            }
            save(bean);
        }
    }
}
