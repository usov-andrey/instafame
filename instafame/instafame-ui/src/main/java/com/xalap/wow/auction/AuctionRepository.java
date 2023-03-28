package com.xalap.wow.auction;

import com.xalap.framework.data.page.repository.PageableRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Usov Andrey
 * @since 02.06.2022
 */
@Repository
public interface AuctionRepository extends PageableRepository<AuctionBean, Long> {
}
