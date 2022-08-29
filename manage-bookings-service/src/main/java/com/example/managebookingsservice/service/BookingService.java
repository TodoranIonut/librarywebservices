package com.example.managebookingsservice.service;

import com.example.managebookingsservice.requestDTO.ReservationBookRequestBody;

public interface BookingService {

    void reserveBook(Integer bookId, String username);

    void confirmReservation(Integer bookId);

    void cancelReservation(Integer bookId);

    void returnBook(Integer bookId);

}