package com.example.managebooksservice.service;

import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.managebooksservice.domain.Status.AVAILABLE;
import static com.example.managebooksservice.domain.Status.UNAVAILABLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ManageBooksServiceImplementationTest {
    //integration
    @Mock private BookRepository bookRepository;
    private ManageBooksService serviceUnderTest;

    @BeforeEach
    void setUp() {
        serviceUnderTest = new ManageBooksServiceImplementation(bookRepository);
    }


    @Test
    void getAllBooksTest() {
        //when - get all books from service
        serviceUnderTest.findAllBooks();
        //then - check if finAll() method is being used
        verify(bookRepository).findAll();
    }

    @Test
    void saveBookTest() {

        //given - book init
        Book book = new Book("Title Save Test", "Author save Test");

        //when - add book throw service
        serviceUnderTest.addBook(book);

        //then - capture book type init
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        // verify that save method is being used by mock repository, capture the Book from save method
        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        //verify if captured book is same as added book
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void updateBook() {

        //given - book init
        Book book = new Book(1,"Title Save Test", "Author save Test","AVAILABLE");
        serviceUnderTest.addBook(book);

        //when - set book new title
        book.setTitle("New title test");
        book.setAuthor("New author test");
        serviceUnderTest.updateBook(1,book);

        //then - capture book type init
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        // verify that save method is being used by mock repository, capture the Book from save method
        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        //verify if captured book is same as added book
        assertThat(capturedBook).isEqualTo(book);


    }

    @Test
    void deleteBook() {

        //given - book init
        Book book = new Book(1,"Title Save Test", "Author save Test","AVAILABLE");
        serviceUnderTest.addBook(book);

        //when - add book throw service
        serviceUnderTest.deleteBook(1);

        //then
        verify(bookRepository).deleteById(1);

    }

    @Test
    void findBookById() {

        //given - book init
        Book book = new Book(1,"Title Save Test", "Author save Test","AVAILABLE");
        serviceUnderTest.addBook(book);

        //when
        serviceUnderTest.findBookById(1);

        //then
        verify(bookRepository).findById(1);
    }

    @Test
    void findBookByTitle() {

        //given - book init
        String bookTitle = "Find Book Title Test";
        Book book = new Book(bookTitle, "Book Update Test");
        Book book2 = new Book(1,bookTitle, "Book Update Test","AVAILABLE");
//        serviceUnderTest.addBook(book);
        bookRepository.save(book);

        //when
        serviceUnderTest.findBookByTitle(bookTitle);

        //then
//        verify(bookRepository).findBookByTitle(bookTitle);
    }
}