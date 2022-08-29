package com.example.managebooksservice.controller;

import com.example.managebooksservice.requestDTO.AddBookRequestBody;
import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.service.ManageBooksService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class ManageBooksController {

    private final ManageBooksService manageBooksService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok().body(manageBooksService.findAllBooks());
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<Book> getBookById(@PathVariable @NotNull Integer idBook) {
        return ResponseEntity.ok().body(manageBooksService.findBookById(idBook));
    }

    @PostMapping("/save")
    public ResponseEntity<Book> addBook(@RequestBody AddBookRequestBody addRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/book/save").toUriString());
        Book book = new Book(addRequest.getTitle(),addRequest.getAuthor());
        return ResponseEntity.created(uri).body(manageBooksService.addBook(book));
    }

    @PostMapping("/update/{idBook}")
    public ResponseEntity<Book> updateBook(@PathVariable @NotNull Integer idBook, @RequestBody @NotNull Book newBook) {
        return ResponseEntity.ok().body(manageBooksService.updateBook(idBook, newBook));
    }

    @DeleteMapping("/delete/{idBook}")
    public ResponseEntity<?> deleteBook(@PathVariable @NotNull Integer idBook) {
        manageBooksService.deleteBook(idBook);
        return ResponseEntity.ok().body("book " + idBook + " deleted");
    }




    @PostMapping("/{idBook}/add/user/{idUser}")
    public ResponseEntity<?> reserveBook(@PathVariable @NotNull Integer idBook, @PathVariable @NotNull Integer idUser){
        manageBooksService.reserveBookByUser(idBook, idUser);
        return ResponseEntity.ok().body("Book " + idBook + " assigned to " + idUser);
    }

    @PostMapping("/confirm/reservation/{idBook}")
    public ResponseEntity<?> confirmBookReservation(@PathVariable @NotNull Integer idBook){
        manageBooksService.confirmBookReservation(idBook);
        return ResponseEntity.ok().body("Reserve book " + idBook + " confirmed");
    }

    @PostMapping("/cancel/reservation/{idBook}")
    public ResponseEntity<?> cancelBookReservation(@PathVariable @NotNull Integer idBook){
        manageBooksService.cancelBookReservation(idBook);
        return ResponseEntity.ok().body("Cancel reservation for book " + idBook + " confirmed");
    }

    @PostMapping("/return/{idBook}")
    public ResponseEntity<?> returnBook(@PathVariable @NotNull Integer idBook){
        manageBooksService.returnBook(idBook);
        return ResponseEntity.ok().body("Return book " + idBook);
    }

}
