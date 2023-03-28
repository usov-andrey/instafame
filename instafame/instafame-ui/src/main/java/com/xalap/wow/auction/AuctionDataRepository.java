package com.xalap.wow.auction;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Usov Andrey
 * @since 30.05.2022
 */
@Repository
public interface AuctionDataRepository extends MongoRepository<AuctionDataBean, Integer> {

    Optional<AuctionDataBean> findByRealmIdAndAuctionHouseId(int realmId, int auctionHouseId);

}

