package com.xalap.wow.item;

/**
 * @author Усов Андрей
 * @since 03/12/2019
 */
public enum ItemQuality {
    gray("UNCOMMON"),
    white("COMMON"),
    green("GREEN"),
    blue("RARE"),
    epic("EPIC"),
    legendary("LEGENDARY");

    private final String name;

    ItemQuality(String name) {
        this.name = name;
    }

    public static ItemQuality fromString(String value) {
        for (ItemQuality itemQuality : values()) {
            if (itemQuality.name.equals(value)) {
                return itemQuality;
            }
        }
        throw new IllegalStateException("Not found value in enum by " + value);
    }
}
