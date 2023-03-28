package com.xalap.wow.auction;

/**
 * @author Usov Andrey
 * @since 30.05.2022
 */
public enum AuctionTimeLeft {

    LONG("LONG", 12),
    VERY_LONG("VERY_LONG", 48),
    MEDIUM("MEDIUM", 2),
    SHORT("SHORT", 0.5);


    private final String value;
    private final double hours;//Сколько часов осталось до конца аукциона

    AuctionTimeLeft(String value, double hours) {
        this.value = value;
        this.hours = hours;
    }

    public static AuctionTimeLeft fromString(String value) {
        for (AuctionTimeLeft auctionTimeLeft : values()) {
            if (auctionTimeLeft.getValue().equals(value)) {
                return auctionTimeLeft;
            }
        }
        throw new IllegalStateException("Not found AuctionTimeLeft for " + value);
    }

    public String getValue() {
        return value;
    }

    public double getHours() {
        return hours;
    }

}
