package com.xalap.wow.tsm;

/**
 * @author Usov Andrey
 * @since 31.05.2022
 */
public class TsmItem {

    private TsmItemData data;
    private TsmItemRegionData regionData;

    public TsmItemData getData() {
        return data;
    }

    public void setData(TsmItemData data) {
        this.data = data;
    }

    public TsmItemRegionData getRegionData() {
        return regionData;
    }

    public void setRegionData(TsmItemRegionData regionData) {
        this.regionData = regionData;
    }

    public long getItemId() {
        return data != null ? data.getItemString() : regionData.getItemString();
    }

    @Override
    public String toString() {
        return "TsmItem{" +
                "data=" + data +
                ", regionData=" + regionData +
                '}';
    }
}
