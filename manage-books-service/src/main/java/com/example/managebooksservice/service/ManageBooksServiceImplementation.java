package com.example.managebooksservice.service;


import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.repository.BookRepository;
import com.example.managebooksservice.utils.exceptions.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.managebooksservice.domain.Status.*;


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
    public Book findBookById(Integer idBook) {
        log.info("get book {} details", idBook);
        Book findBook = bookRepository.findById(idBook).orElse(null);
        if (findBook == null)
            throw new BookNotFoundException("Book with id " + idBook + " not found");
        return findBook;
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
            book.setIdBook(newBook.getIdUser());
            bookRepository.save(book);
            return book;
        }
        throw new BookNotFoundException(book);
    }

    @Override
    public void deleteBook(Integer idBook) {
        log.info("delete book {}", idBook);
        findBookById(idBook);
        bookRepository.deleteById(idBook);
    }



    @Override
    public Book findBookByTitle(String title) {
        Book findBook = bookRepository.findBookByTitle(title);
        if (findBook == null)
            throw new BookNotFoundException("Book title " + title + " not found");
        return findBook;
    }

//    @Override
//    public void removeAppUser(Integer idBook) {
//        Book findBook = findBookById(idBook);
//        findBook.removeAppUser();
//        bookRepository.save(findBook);
//    }

    @Override
    public void reserveBookByUser(Integer idBook, Integer idUser) {
        Book findBook = findBookById(idBook);
        if(!findBook.getStatus().equals(AVAILABLE.name()))
            throw new BookNotAvailableException(idBook);
        findBook.setIdUser(idUser);
        findBook.setStatus(RESERVED.name());
        bookRepository.save(findBook);
    }

    @Override
    public void confirmBookReservation(Integer idBook) {
        Book findBook = findBookById(idBook);
        if(!findBook.getStatus().equals(RESERVED.name()))
            throw new BookNotReservedException("Book is not reserved");
        findBook.setStatus(UNAVAILABLE.name());
        bookRepository.save(findBook);
    }

    @Override
    public void returnBook(Integer idBook) {
        Book findBook = findBookById(idBook);
        isBookBooked(findBook);
        findBook.setStatus(AVAILABLE.name());
        findBook.setIdUser(null);
        bookRepository.save(findBook);
    }

    @Override
    public void cancelBookReservation(Integer idBook) {
        Book findBook = findBookById(idBook);
        isBookReserved(findBook);
        findBook.setStatus(AVAILABLE.name());
        findBook.setIdUser(null);
        bookRepository.save(findBook);
    }

    @Override
    public void isBookBooked(Book book){
        isUserAssigned(book);
        if(!book.getStatus().equals(UNAVAILABLE.name()))
            throw new BookNotBookedException("Book is not booked");
    }

    @Override
    public void isBookReserved(Book book){
        isUserAssigned(book);
        if(!book.getStatus().equals(RESERVED.name()))
            throw new BookNotReservedException("Book is not reserved");
    }

    @Override
    public void isUserAssigned(Book book) {
        if(book.getIdUser() == null)
            throw new BookMissingUserException("Book missing assigned user");
    }
}
