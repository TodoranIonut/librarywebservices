package com.example.manageusersservice.utils.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({EmailConflictException.class, UsernameConflictException.class})
    public ResponseEntity<Object> handleConflictException(Exception e){
        //1.create payload with exception details
        HttpStatus conflict = HttpStatus.CONFLICT;

        ExceptionData bookException = new ExceptionData(
                e.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        //2.return response entity
        return new ResponseEntity<>(bookException, conflict);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleUserNotFoundException(Exception e){
        HttpStatus conflict = HttpStatus.NOT_FOUND;
        ExceptionData bookException = new ExceptionData(
                e.getMessage(),
                conflict,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(bookException, conflict);
    }


}
