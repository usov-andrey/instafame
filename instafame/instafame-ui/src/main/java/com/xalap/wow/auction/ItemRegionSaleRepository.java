package com.xalap.wow.auction;

import com.xalap.wow.item.ItemBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 13/08/2019
 */
@Repository
public interface ItemRegionSaleRepository extends PagingAndSortingRepository<ItemRegionSaleBean, Integer> {

    String QUERY = "select s from ItemRegionSaleBean s left join fetch s.item i";


    @Query(QUERY)
    @Override
    List<ItemRegionSaleBean> findAll();

    @Query(QUERY + " where s.item = :item")
    ItemRegionSaleBean findByItem(@Param("item") ItemBean item);

    @Query(QUERY + " where s.item in :items")
    List<ItemRegionSaleBean> findByItems(@Param("items") Collection<ItemBean> items);
}
