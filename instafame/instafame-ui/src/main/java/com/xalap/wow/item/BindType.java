package com.xalap.wow.item;

/**
 * @author Усов Андрей
 * @since 24/11/2019
 */
public enum BindType {
    none("NONE"),
    bop("ON_ACQUIRE"),//bind when picked up
    boe("ON_EQUIP"),//bind when equipped
    bou("ON_USE"),//bind when used
    unique("UNIQUE");//Для задания, уникальный

    private final String name;

    BindType(String name) {
        this.name = name;
    }

    public static BindType fromString(String value) {
        for (BindType bindType : values()) {
            if (bindType.name.equals(value)) {
                return bindType;
            }
        }
        throw new IllegalStateException("Not found value in enum by " + value);
    }
}
