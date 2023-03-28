package com.xalap.wow.item.stats;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.wow.auction.file.AuctionFileBean;
import com.xalap.wow.item.ItemBean;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 28.06.2018
 */
@Repository
public interface ItemStatsRepository extends PageableRepository<ItemStatsBean, Integer> {

    List<ItemStatsBean> findByItem(ItemBean item);

    @Query("select isb from ItemStatsBean isb left join fetch isb.auctionFile af left join fetch isb.item i where isb.item in :items")
    List<ItemStatsBean> findByItems(@Param("items") Collection<ItemBean> items);

    ItemStatsBean findByItemAndAuctionFile(ItemBean item, AuctionFileBean auctionFile);
}
