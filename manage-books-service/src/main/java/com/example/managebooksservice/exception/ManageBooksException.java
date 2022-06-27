package com.example.managebooksservice.exception;

import com.example.managebooksservice.service.ManageBooksService;

public class ManageBooksException extends RuntimeException{

    public ManageBooksException(String message){
        super(message);
    }

    public ManageBooksException(String message, Throwable cause){
        super(message,cause);
    }
}
