package com.example.managebooksservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ManageBooksExceptionHandler {

    @ExceptionHandler(value = {ManageBooksException.class})
    public ResponseEntity<Object> manageBookException(ManageBooksException e){
        //1.create payload containing exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        BookException bookException = new BookException(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2.return response entity

        return new ResponseEntity<>(bookException, badRequest);
    }
}
