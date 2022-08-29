package com.example.manageusersservice.utils.exceptions;

public class InvalidAppUserDetailsException extends RuntimeException{

    public InvalidAppUserDetailsException(){
        super("Invalid app user details exception");
    }

    public InvalidAppUserDetailsException(String message){
        super(message);
    }
}
