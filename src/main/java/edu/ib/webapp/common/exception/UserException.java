package edu.ib.webapp.common.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Klasa rozszerzająca Springową klasę obsługującą wyjątki. Za pomocą jej metody wywoływane są customowe wyjątki.
 */
public class UserException extends ResponseStatusException {

    public UserException(HttpStatus status, ExceptionMessage exceptionMessage) {
        super(status, exceptionMessage.getMessage());
    }

}
