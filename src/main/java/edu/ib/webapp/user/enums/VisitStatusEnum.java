package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * Enum statusów wizyty
 */
@Getter
@RequiredArgsConstructor
public enum VisitStatusEnum {
    WAITING("Czekanie"),
    STARTED("W_trakcie"),
    REJECTED("Odrzucono"),
    UPCOMING("Nadchodząca"),
    FREE("Dostępna"),
    CANCELLED("Odwołana"),
    ENDED("Zakończona");

    private final String type;
}
