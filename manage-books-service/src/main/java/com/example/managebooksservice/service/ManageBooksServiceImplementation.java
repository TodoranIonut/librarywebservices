package com.example.managebooksservice.service;


import com.example.managebooksservice.dto.AddBookRequestBody;
import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.exception.ManageBooksException;
import com.example.managebooksservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManageBooksServiceImplementation implements ManageBooksService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book addBook(Book book) {
        log.info("save book {}", book.toString());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Integer idBook, Book newBook) {
        log.info("update book {}", idBook);
        Book book = findBookById(idBook);
        if (book != null && newBook.getTitle() != null && newBook.getAuthor() != null && newBook.getStatus() != null) {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            book.setStatus(newBook.getStatus());
            bookRepository.save(book);
            return book;
        }
        return null;
    }

    @Override
    public void deleteBook(Integer idBook) {
        log.info("delete book {}", idBook);
        Book findBook = bookRepository.findById(idBook).orElse(null);
        if(findBook == null)
            throw new ManageBooksException("Book with id " + idBook + " not found");
        bookRepository.deleteById(idBook);
    }

    @Override
    public Book findBookById(Integer idBook) {
        log.info("get book {} details", idBook);
        Book findBook = bookRepository.findById(idBook).orElse(null);
        if(findBook == null)
            throw new ManageBooksException("Book with id " + idBook + " not found");
        return findBook;
    }

    @Override
    public Book findBookByTitle(String title) {
        Book findBook = bookRepository.findBookByTitle(title);
        if(findBook == null)
            throw new ManageBooksException("Book title " + title + " not found");
        return findBook;
    }
}
