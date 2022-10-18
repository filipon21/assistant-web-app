package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VisitStatusEnum {
    WAITING("Czekanie"),
    STARTED("W_trakcie"),
    REJECTED("Odrzucono"),
    UPCOMING("Nadchodząca"),
    ENDED("Zakończona");

    private final String type;
}
