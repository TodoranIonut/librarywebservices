package com.example.manageusersservice.repository;

import com.example.manageusersservice.domain.AppUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class AppUserRepositoryTest {

    @Autowired
    private AppUserRepository underTest;

    @BeforeEach
    void setUp(){
        AppUser appUser = new AppUser(
                "username",
                "password",
                "usernameemail"
        );
        underTest.save(appUser);
    }

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void findByUsername() {
        //given
        AppUser appUser = new AppUser(
                "find_username",
                "find_appuser_password",
                "find_app_user_email"
        );
        underTest.save(appUser);

        //when
        AppUser found = underTest.findByUsername("find_username");

        //then
        assertThat(found).isEqualTo(appUser);
    }

    @Test
    void findByEmail() {
        //given
        AppUser appUser = new AppUser(
                "find_username",
                "find_appuser_password",
                "find_app_user_email"
        );
        underTest.save(appUser);

        //when
        AppUser found = underTest.findByEmail("find_app_user_email");

        //then
        assertThat(found).isEqualTo(appUser);
    }
}