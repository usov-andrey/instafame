package com.xalap.wow.professionspell;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.wow.craft.Profession;
import com.xalap.wow.item.ItemBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Repository
public interface SpellRepository extends PageableRepository<SpellBean, Integer> {

    String QUERY = "select r from SpellBean r left join fetch r.item i " +
            "left join fetch r.item ri";

    @Query(QUERY)
    @Override
    List<SpellBean> findAll();

    SpellBean findByItemAndRank(ItemBean itemBean, String rank);

    @Query(QUERY + " where r.profession = :profession")
    List<SpellBean> findByProfession(@Param("profession") Profession profession);
}
