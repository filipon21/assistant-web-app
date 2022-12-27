package edu.ib.webapp.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum służący do określenia kierunku sortowania
 */
@Getter
@AllArgsConstructor
public enum SortingDirectionEnum {

    ASCENDING("asc"),
    DESCENDING("desc"),
    DEFAULT_SORT_DIRECTION("desc");

    private final String direction;
}
