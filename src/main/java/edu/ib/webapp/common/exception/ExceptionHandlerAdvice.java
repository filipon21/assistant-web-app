package edu.ib.webapp.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({UserException.class, RoleException.class})
    public ResponseEntity handleException(UserException e) {
        // log exception
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}