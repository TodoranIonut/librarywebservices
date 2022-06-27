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
}