package com.example.managebookingsservice.controller;

import com.example.managebookingsservice.dao.ReserveBookRequestDAO;
import com.example.managebookingsservice.service.BookingService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/reserve/create")
    public ResponseEntity<String> reserveBook(@RequestBody ReserveBookRequestDAO reserve) {
        bookingService.bookReservation(reserve.getBookId(), reserve.getUsername());
        return ResponseEntity.ok().body("Book id " + reserve.getBookId() + " is reserved by " + reserve.getUsername());
    }

    @PostMapping("/reserve/confirm/{idBook}")
    public ResponseEntity<String> confirmReservedBook(@PathVariable @NotNull Integer idBook) {
        bookingService.confirmReservation(idBook);
        return ResponseEntity.ok().body("Book id " + idBook + " is reserved");
    }

    @PostMapping("/reserve/cancel/{idBook}")
    public ResponseEntity<String> cancelReservedBook(@PathVariable @NotNull Integer idBook) {
        bookingService.cancelReservation(idBook);
        return ResponseEntity.ok().body("Reservation is Canceled");
    }

    @PostMapping("/return/{idBook}")
    public ResponseEntity<String> returnBook(@PathVariable @NotNull Integer idBook) {
        bookingService.returnBook(idBook);
        return ResponseEntity.ok().body("Return book " + idBook);
    }


}
