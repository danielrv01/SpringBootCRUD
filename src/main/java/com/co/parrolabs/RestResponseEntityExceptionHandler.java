package com.co.parrolabs;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { RuntimeException.class})
    protected ResponseEntity<ErrorMessage> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "The operation could not be completed. Please try again later.";
        ErrorMessage message = new ErrorMessage(bodyOfResponse);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}