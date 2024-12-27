package com.kayas.brokerageFirm.utility.enums;

import lombok.Getter;

@Getter
public enum Status {
    PENDING("P", "The order is pending."),
    MATCHED("M", "The order is matched."),
    CANCELLED("C", "The order is cancelled."),
    FAILED("F", "The order is failed.");


    private final String displayName;
    private final String description;

    Status(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

}

