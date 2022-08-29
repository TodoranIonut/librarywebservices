package com.example.managebooksservice.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {
            BookNotAvailableException.class,
            BookNotReservedException.class,
            BookNotBookedException.class,
            BookNotFoundException.class,
            BookMissingUserException.class,
    })
    public ResponseEntity<Object> handleBooksExceptions(Exception e){
        //1.create payload with exception details
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ExceptionData bookException = new ExceptionData(
                e.getMessage(),
                badRequest,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2.return response entity
        return new ResponseEntity<>(bookException, badRequest);
    }
}
