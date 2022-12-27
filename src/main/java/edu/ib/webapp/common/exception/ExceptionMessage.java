package edu.ib.webapp.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enum służący do wywoływania customowych komunikatów o błędach
 */
@Getter
@RequiredArgsConstructor
public enum ExceptionMessage {

    USER_NOT_FOUND("Użytkownik nie istnieje"),
    USERNAME_MUST_BE_UNIQUE("Nazwa użytkowika (email) musi być unikalna"),
    WORKER_NOT_FOUND("Pracownik nie istnieje"),

    VISIT_NOT_FOUND("Wizyta nie istnieje"),
    CANNOT_CREATE_OR_FIND_VISIT("Użytkownik nie może utworzyć wizyty lub znaleźć dla innego użytkownika"),
    CANNOT_CREATE_NEW_VISIT("Użytkownik jest już podczas wizyty"),
    CANNOT_ADD_NEW_USER("Nie można dodać do wizyty nowego użytkownika"),

    TIME_IS_NOT_CORRECT("Podano błędne daty i godziny"),

    REFFERAL_NOT_FOUND("Skierowanie nie istnieje"),
    BAD_REFFERAL("Skierowanie nie jest do tego lekarza"),
    REFFERAL_IS_NEEDED("Skierowanie jest potrzebne"),

    ROLE_NOT_FOUND("Rola nie istnieje");

    private final String message;
}
