package com.kayas.brokerageFirm.utility.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("A", "Admin"),
    USER("U", "User");

    private final String displayName;
    private final String description;

    Role(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

}
