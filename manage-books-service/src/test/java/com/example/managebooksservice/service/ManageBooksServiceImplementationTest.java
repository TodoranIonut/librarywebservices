package com.example.managebooksservice.service;

import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.repository.BookRepository;
import com.example.managebooksservice.utils.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.example.managebooksservice.domain.Status.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageBooksServiceImplementationTest {

    @Mock private BookRepository bookRepository;
    private ManageBooksService underTest;

    @BeforeEach
    void setUp(){

        underTest = new ManageBooksServiceImplementation(bookRepository);
    }

    @Test
    void findAllBooks() {
        //when
        underTest.findAllBooks();
        //then
        verify(bookRepository).findAll();
    }

    @Test
    void findBookById() {
        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );
        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        underTest.findBookById(1);
        //then
        verify(bookRepository).findById(1);
    }

    @Test
    void findBookThatIsNotExistById() {
        //given
        //when
        //then
        assertThatThrownBy(() -> underTest.findBookById(1))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book with id " + 1 + " not found");
    }

    @Test
    void addBook() {
        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );

        //when
        underTest.addBook(book);

        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void updateBook() {

        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(new Book()));
        underTest.updateBook(1,book);

        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void cannotUpdateBook() {

        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );
        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        //then
        assertThatThrownBy(() -> underTest.updateBook(1,new Book()))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    void deleteBook() {
        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        underTest.deleteBook(1);

        //then
        verify(bookRepository).deleteById(1);

    }

    @Test
    void findBookByTitle() {
        //given
        String title="Mauritio";
        Book book = new Book(
                title,
                "Jabar"
        );

        //when
        when(bookRepository.findBookByTitle(any())).thenReturn(book);
        underTest.findBookByTitle(title);
        underTest.findBookByTitle(title);

        //then
        verify(bookRepository,times(2)).findBookByTitle(title);
    }

    @Test
    void findBookThatNotExistByTitle() {
        //given
        String title="Mauritio";

        //when
        //then
        assertThatThrownBy(()->underTest.findBookByTitle(title))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book title " + title + " not found");
    }

    @Test
    void reserveBookByUser() {
        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        underTest.reserveBookByUser(1,1);

        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void reserveNotExistingBookByUser() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(RESERVED),
                1
        );
        book.setStatus(String.valueOf(RESERVED));

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //then
        Integer bookId = 1;
        assertThatThrownBy(() -> underTest.reserveBookByUser(bookId,1))
                .isInstanceOf(BookNotAvailableException.class)
                .hasMessageContaining("Book id " + bookId +" is not available");
    }

    @Test
    void confirmBookReservation() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(RESERVED),
                1
        );

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        underTest.confirmBookReservation(1);

        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void confirmBookNotReserved() {
        //given
        Book book = new Book(
                "Mauritio",
                "Jabar"
        );

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));

        //then
        Integer bookId = 1;
        assertThatThrownBy(() -> underTest.confirmBookReservation(bookId))
                .isInstanceOf(BookNotReservedException.class)
                .hasMessageContaining("Book is not reserved");
    }

    @Test
    void returnBook() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(UNAVAILABLE),
                1
        );

        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        underTest.returnBook(1);

        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void cancelBookReservation() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(RESERVED),
                1
        );
        //when
        when(bookRepository.findById(any())).thenReturn(Optional.of(book));
        underTest.cancelBookReservation(1);

        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);

        verify(bookRepository).save(bookArgumentCaptor.capture());

        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    void isBookNotBooked() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(AVAILABLE),
                1
        );

        //when
        //then
        assertThatThrownBy(() -> underTest.isBookBooked(book))
                .isInstanceOf(BookNotBookedException.class)
                .hasMessageContaining("Book is not booked");
    }

    @Test
    void isBookNotReserved() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(UNAVAILABLE),
                1
        );
        //when
        //then
        assertThatThrownBy(() -> underTest.isBookReserved(book))
                .isInstanceOf(BookNotReservedException.class)
                .hasMessageContaining("Book is not reserved");
    }

    @Test
    void isUserNotAssigned() {
        //given
        Book book = new Book(
                1,
                "Mauritio",
                "Jabar",
                String.valueOf(AVAILABLE),
                null
        );

        //when
        //then
        assertThatThrownBy(() -> underTest.isUserAssigned(book))
                .isInstanceOf(BookMissingUserException.class)
                .hasMessageContaining("Book missing assigned user");

    }
}