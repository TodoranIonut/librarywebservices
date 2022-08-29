package com.example.managebooksservice.utils.exceptions;

public class BookNotReservedException extends RuntimeException{

    public BookNotReservedException(String message){
        super(message);
    }

    public BookNotReservedException(String message, Throwable cause){
        super(message,cause);
    }
}
