package com.example.managebooksservice.repository;

import com.example.managebooksservice.domain.Book;
import com.example.managebooksservice.exception.ManageBooksException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepositoryUnderTest;

    @AfterEach
    void tearDown() {
        bookRepositoryUnderTest.deleteAll();
    }

    @Test
    void findBookByTitle() {

        //given
        String title = "title test";
        String author = "author test";
        Book book = new Book(title, author);
        bookRepositoryUnderTest.save(book);

        //when
        Book bookFound = bookRepositoryUnderTest.findBookByTitle("title test");

        //then
        assertThat(bookFound).isEqualTo(book);
    }

    @Test
    void notFindBookByTitleThrowException() {

        //given
        String title = "title test";
        String author = "author test";
        Book book = new Book(title, author);
//        bookRepositoryUnderTest.save(book);

        given(bookRepositoryUnderTest.findBookByTitle(title))
                .willReturn(null);

        //when


        //then
        assertThatThrownBy(()-> bookRepositoryUnderTest.findBookByTitle(title))
                .isInstanceOf(ManageBooksException.class)
                .hasMessageContaining("Book title " + title + " not found");

        verify(bookRepositoryUnderTest,never()).save(any());
    }
}