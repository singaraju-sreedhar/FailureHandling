package com.sre.retrytimeout.paymentservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExpHandler  {

    @ExceptionHandler(ApiResponseException.class)
    public ResponseEntity<String> ApiResponseExceptionHandler(ApiResponseException apiEx) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                body("Controller Advice global exception handler : "
                        +" Custom data : "+apiEx.getExpDataObject()
                        +" Reason: "+ apiEx.getOriginalException().getMessage());
    }

}
