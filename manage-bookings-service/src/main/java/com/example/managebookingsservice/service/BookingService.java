package com.example.managebookingsservice.service;

public interface BookingService {

    void bookReservation(Integer bookId, String username);

    void confirmReservation(Integer bookId);

    void cancelReservation(Integer bookId);

    void returnBook(Integer bookId);
}