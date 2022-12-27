package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum statusów skierowań
 */
@Getter
@RequiredArgsConstructor
public enum RefferalStatusEnum {

    ISSUED("Wystawiono"),
    COMPLETED("Zrealizowano");

    private final String status;
}
