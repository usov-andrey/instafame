package com.xalap.wow.auction.file;

import com.xalap.crm.service.scheduler.SchedulerService;
import com.xalap.framework.data.CrudService;
import com.xalap.framework.utils.HttpClientUtils;
import com.xalap.wow.api.Region;
import com.xalap.wow.api.WowApi;
import com.xalap.wow.api.auction.AuctionsFilesResponse;
import com.xalap.wow.api.auction.WowFile;
import com.xalap.wow.auction.UpdateAuctionsTask;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 15/11/2019
 */
@Service
public class AuctionFileService extends CrudService<AuctionFileBean, AuctionFileRepository, Integer> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private WowApi api;
    @Autowired
    private SchedulerService schedulerService;
    @Autowired
    private Environment environment;

    private AuctionFileBean currentAuctionFile;

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        schedulerService.register(UpdateAuctionsTask.class, "Обновление WOW аукционов");
    }

    public AuctionFileBean getCurrentAuctionFile() {
        return currentAuctionFile;
    }

    public void setCurrentAuctionFile(AuctionFileBean currentAuctionFile) {
        this.currentAuctionFile = currentAuctionFile;
    }

    /**
     * Создаем бин, но не пишем в базу
     */
    public AuctionFileBean createAuctionFileBean(Region region, Date dataTime) {
        AuctionFileBean fileBean = new AuctionFileBean();
        fileBean.setCreateTime(new Date());
        fileBean.setDataTime(dataTime);
        fileBean.setRegion(region);
        return fileBean;
    }

    public void updateAuction(Region region) {
        AuctionsFilesResponse actionFilesResponse = api.auction(region);
        for (WowFile wowFile : actionFilesResponse.getFiles()) {
            Date dataTime = new Date(wowFile.getLastModified());
            if (repository().findByRegionAndDataTime(region, dataTime).isEmpty()) {
                AuctionFileBean fileBean = createAuctionFileBean(region, dataTime);

                Path auctionFilePath = filePath(fileBean);
                Request get = Request.Get(wowFile.getUrl());
                try {
                    Response response = HttpClientUtils.exec(get, 150);
                    InputStream content = response.returnResponse().getEntity().getContent();
                    Files.copy(content, auctionFilePath, StandardCopyOption.REPLACE_EXISTING);
                    save(fileBean);
                } catch (IOException e) {
                    throw new IllegalStateException("Error on process " + auctionFilePath + " exec request:" + get);
                }
            }
        }
    }

    public Path filePath(AuctionFileBean bean) {
        return Paths.get(filesPath(), bean.getFileName());
    }

    private String filesPath() {
        return environment.getProperty("file.path");
    }

    /*
     * Обновить все аукионы
     */
    public void updateAuctions() {
        for (Region region : Region.values()) {
            updateAuction(region);
        }
    }
}
