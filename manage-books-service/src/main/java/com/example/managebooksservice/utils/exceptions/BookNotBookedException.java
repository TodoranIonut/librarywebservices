package com.example.managebooksservice.utils.exceptions;

public class BookNotBookedException extends RuntimeException{

    public BookNotBookedException(String message){
        super(message);
    }

    public BookNotBookedException(String message, Throwable cause){
        super(message,cause);
    }
}
