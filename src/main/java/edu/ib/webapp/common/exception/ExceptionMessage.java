package edu.ib.webapp.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    USER_NOT_FOUND("Użytkownik nie istnieje"),
    USERNAME_MUST_BE_UNIQUE("Nazwa użytkowika (email) musi być unikalna"),
    ASSISTANT_NOT_FOUND("Asystent nie istnieje"),

    VISIT_NOT_FOUND("Wizyta nie istnieje"),
    CANNOT_CREATE_VISIT("Użytkownik nie może utworzyć wizyty dla innego użytkownika"),
    CANNOT_CREATE_NEW_VISIT("Użytkownik jest już podczas wizyty"),

    ROLE_NOT_FOUND("Rola nie istnieje");

    private final String message;
}
