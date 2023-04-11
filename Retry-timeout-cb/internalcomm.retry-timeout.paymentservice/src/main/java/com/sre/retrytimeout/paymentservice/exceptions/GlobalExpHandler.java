package com.sre.retrytimeout.paymentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExpHandler  {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleUserNotFoundException(CustomException ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

}
