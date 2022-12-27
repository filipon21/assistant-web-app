package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
/**
 * Enum typów wizyty
 */
@Getter
@RequiredArgsConstructor
public enum VisitTypeEnum {
    PHONE("Telefoniczna"),
    STATIONARY("Stacjonarna"),
    CHAT("Czat");

    private final String type;
}
