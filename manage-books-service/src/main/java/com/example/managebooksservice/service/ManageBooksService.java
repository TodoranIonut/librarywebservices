package com.example.managebooksservice.service;

import com.example.managebooksservice.dao.AddBookRequestBody;
import com.example.managebooksservice.domain.Book;

import java.util.List;

public interface ManageBooksService {

    List<Book> findAllBooks();

    Book saveBook(AddBookRequestBody newBook);

    Book updateBook(Integer idBook, AddBookRequestBody newBook);

    void deleteBook(Integer idBook);

    Book findBookById(Integer idBook);

}