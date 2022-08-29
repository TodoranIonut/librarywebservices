package com.example.managebooksservice.utils.exceptions;

public class BookMissingUserException extends RuntimeException{

    public BookMissingUserException(String message){
        super(message);
    }

    public BookMissingUserException(String message, Throwable cause){
        super(message,cause);
    }
}
