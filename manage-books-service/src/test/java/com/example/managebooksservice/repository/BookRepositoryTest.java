package com.example.managebooksservice.repository;

import com.example.managebooksservice.domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.managebooksservice.domain.Status.AVAILABLE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository underTest;

    @BeforeEach
    void setUp(){
        Book book = new Book(
                "abc",
                "ABC"
        );
        underTest.save(book);
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }


    @Test
    void findBookByTitleTest(){

        //given
        String title = "Mauritio";
        Book book = new Book(
                title,
                "Jabar"
        );
        underTest.save(book);

        //when
        Book found = underTest.findBookByTitle(title);
        //then

        assertThat(found.getTitle()).isEqualTo(title);
    }

    @Test
    void bookNotFindByTitleTest(){

        //given
        String title = "Mauritio";

        //when
        Book found = underTest.findBookByTitle(title);

        //then
        assertThat(found).isEqualTo(null);
    }

}