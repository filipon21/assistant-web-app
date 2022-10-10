package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TelevisitStatusEnum {
    WAITING("Czekanie"),
    STARTED("W_trakcie"),
    REJECTED("Odrzucono"),
    ENDED("Zakończona");

    private final String type;
}
