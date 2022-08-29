package com.example.manageusersservice.utils.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;

public class EmailConflictException extends RuntimeException{

//    public EmailConflictException(String message){
//        super(message);
//    }

    public EmailConflictException(String email){
        super(String.format("Error message for email %s",email));
    }
}
