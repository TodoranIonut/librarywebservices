package com.example.manageusersservice.utils.exceptions;

public class UsernameConflictException extends RuntimeException{

    public UsernameConflictException(String message){
        super(message);
    }

    public UsernameConflictException(String message, Throwable cause){
        super(message,cause);
    }
}
