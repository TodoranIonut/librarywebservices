package com.example.managebooksservice.service;

import com.example.managebooksservice.domain.Book;

import java.util.List;

public interface ManageBooksService {

    List<Book> findAllBooks();

    Book addBook(Book book);

    Book updateBook(Integer idBook, Book book);

    void deleteBook(Integer idBook);

    Book findBookById(Integer idBook);

    Book findBookByTitle(String title);

//    void removeAppUser(Integer idBook);

    void reserveBookByUser(Integer idBook, Integer idUser);

    void confirmBookReservation(Integer idBook);

    void returnBook(Integer idBook);

    void isBookBooked(Book book);

    void isBookReserved(Book book);

    void isUserAssigned(Book book);

    void cancelBookReservation(Integer idBook);
}