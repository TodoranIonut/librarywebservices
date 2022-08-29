package com.example.managebookingsservice.controller;

import com.example.managebookingsservice.requestDTO.ReservationBookRequestBody;
import com.example.managebookingsservice.service.BookingService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/reservation/create")
    public ResponseEntity<String> reserveBook(@RequestBody ReservationBookRequestBody reserve) {
        bookingService.reserveBook(reserve.getBookId(), reserve.getUsername());
        return ResponseEntity.ok().body("Book id " + reserve.getBookId() + " is reserved by " + reserve.getUsername());
    }

    @PostMapping("/reservation/confirm/{idBook}")
    public ResponseEntity<String> confirmReservedBook(@PathVariable @NotNull Integer idBook) {
        bookingService.confirmReservation(idBook);
        return ResponseEntity.ok().body("Book id " + idBook + " is reserved");
    }

    @PostMapping("/reservation/cancel/{idBook}")
    public ResponseEntity<String> cancelReservedBook(@PathVariable @NotNull Integer idBook) {
        bookingService.cancelReservation(idBook);
        return ResponseEntity.ok().body("Book reservation is canceled");
    }

    @PostMapping("/return/book/{idBook}")
    public ResponseEntity<String> returnBook(@PathVariable @NotNull Integer idBook) {
        bookingService.returnBook(idBook);
        return ResponseEntity.ok().body("Book returned");
    }
}
