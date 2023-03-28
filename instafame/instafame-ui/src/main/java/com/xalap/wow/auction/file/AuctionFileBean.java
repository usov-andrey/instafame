package com.xalap.wow.auction.file;

import com.xalap.framework.domain.annotation.FieldName;
import com.xalap.framework.domain.holder.IdHolder;
import com.xalap.wow.api.Region;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Усов Андрей
 * @since 13/08/2019
 */
@Entity()
@Table(name = AuctionFileBean.NAME)
public class AuctionFileBean implements IdHolder<Integer> {

    public static final String NAME = "WOW$AuctionFile";
    public static final String CREATE_TIME = "createTime";
    public static final String DATA_TIME = "dataTime";
    public static final String REGION = "region";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "sequence-generator" + NAME)
    @SequenceGenerator(name = "sequence-generator" + NAME,
            sequenceName = "SEQ_" + NAME)
    private Integer id;
    @FieldName(REGION)
    private Region region;
    @FieldName(CREATE_TIME)
    private Date createTime;
    @FieldName(DATA_TIME)
    private Date dataTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public String getFileName() {
        String filePath = "auctions-%s-%s.json";
        return String.format(filePath, getRegion().name(), getDataTime().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionFileBean that = (AuctionFileBean) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "AuctionFileBean{" +
                "dataTime=" + dataTime +
                ", region=" + region +
                '}';
    }
}
