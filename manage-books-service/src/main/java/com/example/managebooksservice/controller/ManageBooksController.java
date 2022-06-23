package com.example.managebooksservice.controller;

import com.example.managebooksservice.dao.AddBookRequestBody;
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
@RequestMapping("/book")
@RequiredArgsConstructor
public class ManageBooksController {

    private final ManageBooksService booksService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok().body(booksService.findAllBooks());
    }

    @GetMapping("/{idBook}")
    public ResponseEntity<Book> getBookById(@PathVariable @NotNull Integer idBook) {
        return ResponseEntity.ok().body(booksService.findBookById(idBook));
    }

    @PostMapping("/save")
    public ResponseEntity<Book> addBook(@RequestBody AddBookRequestBody addRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/book/save").toUriString());
        return ResponseEntity.created(uri).body(booksService.saveBook(addRequest));
    }

    @PostMapping("/update/{idBook}")
    public ResponseEntity<Book> updateBook(@PathVariable @NotNull Integer idBook, @RequestBody @NotNull AddBookRequestBody addRequest) {
        return ResponseEntity.ok().body(booksService.updateBook(idBook, addRequest));
    }

    @DeleteMapping("/delete/{idBook}")
    public ResponseEntity<?> deleteBook(@PathVariable @NotNull Integer idBook) {
        booksService.deleteBook(idBook);
        return ResponseEntity.ok().body("book " + idBook + " deleted");
    }

}
