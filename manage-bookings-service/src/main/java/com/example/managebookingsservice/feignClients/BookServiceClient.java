package com.example.managebookingsservice.feignClients;

import com.example.managebookingsservice.domain.AppUser;
import com.example.managebookingsservice.domain.Book;
import com.example.managebookingsservice.requestDTO.AddBookRequestBody;
import com.sun.istack.NotNull;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "manage-book-service", url = "localhost:8081/api/book")
public interface BookServiceClient {

//    @RequestMapping(method = RequestMethod.GET, value = "/{idBook}")
    @GetMapping("/{idBook}")
    ResponseEntity<Book> getBookById(@PathVariable @NotNull Integer idBook);

    @PostMapping("/save")
    ResponseEntity<Book> addBook(@RequestBody @NotNull AddBookRequestBody request);

    @PostMapping("/update/{idBook}")
    @ResponseBody
    ResponseEntity<Book> updateBook(@PathVariable @NotNull Integer idBook, @RequestBody @NotNull Book book);

//    @RequestMapping(method = RequestMethod.POST, value = "/update/{idBook}", consumes = "application/json")
//    @RequestLine("POST /update/{idBook}")
//    @Headers("Content-Type: application/json")
//    ResponseEntity<Book> updateBook(@PathVariable @NotNull Integer idBook, @RequestBody @NotNull Book book);


    @DeleteMapping("/delete/{idBook}")
    ResponseEntity<?> deleteBook(@PathVariable @NotNull Integer idBook);


    @PostMapping("/{idBook}/add/user/{idUser}")
    ResponseEntity<String> reserveBook(@PathVariable @NotNull Integer idBook, @PathVariable @NotNull Integer idUser);
//    @RequestMapping(method = RequestMethod.POST, value = "/{idBook}/add/user/{idUser}")//, consumes = "application/json")
//    ResponseEntity<String> reserveBook(@PathVariable @NotNull Integer idBook, @PathVariable @NotNull Integer idUser);

    @PostMapping("/confirm/reservation/{idBook}")
    ResponseEntity<String> confirmBookReservation(@PathVariable @NotNull Integer idBook);

    @PostMapping("/cancel/reservation/{idBook}")
    ResponseEntity<String> cancelBookReservation(@PathVariable @NotNull Integer idBook);

    @PostMapping("/return/{idBook}")
    ResponseEntity<String> returnBook(@PathVariable @NotNull Integer idBook);

}
