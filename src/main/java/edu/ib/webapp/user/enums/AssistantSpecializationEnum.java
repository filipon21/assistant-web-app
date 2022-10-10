package edu.ib.webapp.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AssistantSpecializationEnum {
    RECRUIT("Rekrut"),
    PRACTICED("Doświadczony");

    private final String specialization;
}
