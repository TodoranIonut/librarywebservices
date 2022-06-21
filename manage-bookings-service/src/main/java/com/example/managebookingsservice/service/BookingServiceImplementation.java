package com.example.managebookingsservice.service;


//import com.example.managebookingsservice.domain.AppUser;

import com.example.managebookingsservice.domain.AppUser;
import com.example.managebookingsservice.domain.Book;
import com.example.managebookingsservice.repository.AppUserRepository;
import com.example.managebookingsservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.managebookingsservice.domain.Status.*;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookingServiceImplementation implements BookingService {

    private final BookRepository bookRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public void bookReservation(Integer bookId, String username) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getStatus().equals(AVAILABLE.name())) {
            AppUser appUser = appUserRepository.findByUsername(username);
            if (appUser != null) {
                log.info("Set book to user {}", username);
                book.setAppUser(appUser);
                log.info("Set book status RESERVED");
                book.setStatus(RESERVED.name());
                log.info("Add book to user book list");
                appUser.addBook(book);
                log.info("Save book");
                bookRepository.save(book);
            }
        }
    }

    @Override
    public void confirmReservation(Integer bookId) {
        log.info("confirm reserved book with id {}", bookId);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getStatus().equals(RESERVED.name()) && book.getAppUser() != null) {
            log.info("set book status UNAVAILABLE for book with id {}", bookId);
            book.setStatus(UNAVAILABLE.name());
            bookRepository.save(book);
        }
    }

    @Override
    public void cancelReservation(Integer bookId) {
        log.info("cancel reserved book with id {}", bookId);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getStatus().equals(RESERVED.name()) && book.getAppUser() != null) {
            log.info("set book status AVAILABLE for book with id {}", bookId);
            book.setStatus(AVAILABLE.name());
            bookRepository.save(book);
        }
    }

    @Override
    public void returnBook(Integer bookId) {
        log.info("cancel reserved book with id {}", bookId);
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            AppUser appUser = book.getAppUser();
            if (appUser != null && book.getStatus().equals(UNAVAILABLE.name())) {
                log.info("set book status AVAILABLE for book with id {}", bookId);
                book.setAppUser(null);
                book.setStatus(AVAILABLE.name());
                appUser.removeBook(bookId);
                bookRepository.save(book);
            }
        }
    }
}
