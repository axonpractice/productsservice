package com.adedev.estore.productsservice.core.errorhandling;

import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class ProductServiceErrorHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalStateException(IllegalArgumentException e, WebRequest webRequest) {
        var errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {CommandAcceptanceException.class})
    public ResponseEntity<Object> handleCommandException(CommandAcceptanceException e, WebRequest webRequest) {
        var errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleGeneralException(Exception e, WebRequest webRequest) {
        var errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
