package com.example.managebooksservice.utils.exceptions;

public class BookNotAvailableException extends RuntimeException{

    public BookNotAvailableException(Integer idBook){
        super(String.format("Book id %s is not available",idBook));
    }

    public BookNotAvailableException(String message, Throwable cause){
        super(message,cause);
    }
}
