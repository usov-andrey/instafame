package com.xalap.wow.professionspell.reagent;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.wow.craft.Profession;
import com.xalap.wow.item.ItemBean;
import com.xalap.wow.professionspell.SpellBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 28/05/2019
 */
@Repository
public interface ReceiptReagentRepository extends PageableRepository<ReceiptReagentBean, Integer> {

    String QUERY = "select rr from ReceiptReagentBean rr left join fetch rr.item i left join fetch rr.receipt r " +
            "left join fetch r.item ri";

    @Query(QUERY)
    @Override
    List<ReceiptReagentBean> findAll();

    @Query(QUERY + " where r.profession = :profession")
    List<ReceiptReagentBean> findByProfession(@Param("profession") Profession profession);

    ReceiptReagentBean findByReceiptAndItem(SpellBean receiptBean, ItemBean itemBean);
}
