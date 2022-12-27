package edu.ib.webapp.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Klasa służąca do wywoływania customowych wyjątków w całej aplikacji. Wskazywana jest klasa,
 * która będzie mogła je wywoływać
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({UserException.class})
    public ResponseEntity handleException(UserException e) {
        // log exception
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}