package com.example.managebookingsservice.service;

import com.example.managebookingsservice.domain.AppUser;
import com.example.managebookingsservice.domain.Book;
import com.example.managebookingsservice.feignClients.BookServiceClient;
import com.example.managebookingsservice.feignClients.UsersServiceClient;
import com.example.managebookingsservice.requestDTO.ReservationBookRequestBody;
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

    private final BookServiceClient booksServiceClient;
    private final UsersServiceClient appUserServiceClient;

    @Override
    public void reserveBook(Integer bookId, String username) {
        AppUser appUser = appUserServiceClient.getUserByUsername(username).getBody();
        booksServiceClient.reserveBook(bookId, appUser.getIdUser());
    }


    @Override
    public void confirmReservation(Integer bookId) {
        log.info("confirm reservation for book with id {}", bookId);
        booksServiceClient.confirmBookReservation(bookId);
    }

    @Override
    public void cancelReservation(Integer bookId) {
        log.info("cancel reservation for book with id {}", bookId);
        booksServiceClient.cancelBookReservation(bookId);
    }

    @Override
    public void returnBook(Integer bookId) {
        log.info("return book with id {}",bookId);
        booksServiceClient.returnBook(bookId);
    }
}
