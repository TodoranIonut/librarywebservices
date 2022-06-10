package com.example.bookservice.api;

import com.domain.Book;
import com.example.bookservice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private BookService bookService;

    @GetMapping("/getAll")
    public List<Book> findAllBooks() {
        return bookService.findAllBooks();
    }

    @GetMapping
    public String sp() {
        return "merge";
    }
}
