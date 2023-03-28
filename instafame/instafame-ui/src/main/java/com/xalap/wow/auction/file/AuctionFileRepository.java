package com.xalap.wow.auction.file;

import com.xalap.framework.data.page.repository.PageableRepository;
import com.xalap.wow.api.Region;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 13/08/2019
 */
@Repository
public interface AuctionFileRepository extends PageableRepository<AuctionFileBean, Integer> {

    List<AuctionFileBean> findByRegion(Region region);

    List<AuctionFileBean> findByRegionAndDataTime(Region region, Date dataTime);
}
