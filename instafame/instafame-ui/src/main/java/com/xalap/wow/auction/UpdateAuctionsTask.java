package com.xalap.wow.auction;

import com.xalap.crm.service.scheduler.SchedulerTask;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Усов Андрей
 * @since 15/11/2019
 */
public class UpdateAuctionsTask extends SchedulerTask {

    @Autowired
    private AuctionService auctionService;

    @Override
    protected void doRun() {
        auctionService.updateAuctions();
    }
}
