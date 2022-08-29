package com.example.managebooksservice.utils.exceptions;

import com.example.managebooksservice.domain.Book;

public class BookNotFoundException extends RuntimeException{

//    public BookNotFoundException(String message ){
//        super(message);
//    }

    public BookNotFoundException(Book book){
        super(String.format("%s book not found",book));
    }
    public BookNotFoundException(String message){
        super(message);
    }

    public BookNotFoundException(String message, Throwable cause){
        super(message,cause);
    }
}
