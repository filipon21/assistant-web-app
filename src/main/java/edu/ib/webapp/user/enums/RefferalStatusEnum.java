package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RefferalStatusEnum {

    ISSUED("Wystawiono"),
    COMPLETED("Zrealizowano");

    private final String status;
}
