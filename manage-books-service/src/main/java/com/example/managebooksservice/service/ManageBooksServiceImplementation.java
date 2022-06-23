package com.example.managebooksservice.service;


//import com.example.managebooksservice.domain.AppUser;

import com.example.managebooksservice.dao.AddBookRequestBody;
import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public Book saveBook( AddBookRequestBody newBook) {
        log.info("save book " + newBook);
        Book book = new Book(newBook.getTitle(),newBook.getAuthor());
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Integer idBook, AddBookRequestBody newBook) {
        log.info("update book {}", idBook);
        Book book = findBookById(idBook);
        if (book != null && newBook.getTitle() != null && newBook.getAuthor() != null) {
            book.setTitle(newBook.getTitle());
            book.setAuthor(newBook.getAuthor());
            bookRepository.save(book);
            return book;
        }
        return null;
    }

    @Override
    public void deleteBook(Integer idBook) {
        log.info("delete book {}", idBook);
        bookRepository.deleteById(idBook);
    }

    @Override
    public Book findBookById(Integer idBook) {
        log.info("get book {} details", idBook);
        return bookRepository.findById(idBook).orElse(null);
    }
}
