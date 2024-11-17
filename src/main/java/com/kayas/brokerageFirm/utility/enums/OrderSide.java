package com.kayas.brokerageFirm.utility.enums;

import lombok.Getter;

@Getter
public enum OrderSide {
    BUY("B", "Buy"),
    SELL("S", "Sell");

    private final String displayName;
    private final String description;

    OrderSide(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

}
