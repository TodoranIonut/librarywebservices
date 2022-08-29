package com.example.manageusersservice.service;

import com.example.manageusersservice.domain.AppUser;
import com.example.manageusersservice.domain.Permission;
import com.example.manageusersservice.repository.AppUserRepository;
import com.example.manageusersservice.repository.RoleRepository;
import com.example.manageusersservice.requestDTO.AppUserRegisterRequestBody;
import com.example.manageusersservice.utils.exceptions.EmailConflictException;
import com.example.manageusersservice.utils.exceptions.InvalidAppUserDetailsException;
import com.example.manageusersservice.utils.exceptions.UserNotFoundException;
import com.example.manageusersservice.utils.exceptions.UsernameConflictException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageUsersServiceImplementationTest {

    @Mock private AppUserRepository appUserRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    private ManageUsersServiceImplementation underTest;

    @BeforeEach
    void setUp() {
        underTest = new ManageUsersServiceImplementation(appUserRepository,roleRepository,passwordEncoder);
    }

    @Test
    void getAppUsers() {
        //when
        underTest.getAppUsers();
        //then
        verify(appUserRepository).findAll();
    }

    @Test
    void saveAppUser() {

        //given
        AppUserRegisterRequestBody appUserRequest = new AppUserRegisterRequestBody(
                "appuser_username",
                "appuser_password",
                "appuser_email");
        AppUser newAppUser = new AppUser(
                appUserRequest.getUsername(),
                passwordEncoder.encode(appUserRequest.getPassword()),
                appUserRequest.getEmail());

        //when
        underTest.saveAppUser(appUserRequest);

        //then
        ArgumentCaptor<AppUser> appUserCaptor = ArgumentCaptor.forClass(AppUser.class);

        verify(appUserRepository).save(appUserCaptor.capture());

        AppUser capturedUser = appUserCaptor.getValue();
        assertThat(capturedUser).isEqualTo(newAppUser);
    }

    @Test
    void addRoleToUser() {
    }

    @Test
    void usernameAvailable() {
        //given
        //when
        when(appUserRepository.findByUsername(any())).thenReturn(new AppUser());
        underTest.findByUsername("appuser_username");
        //then
        verify(appUserRepository).findByUsername("appuser_username");
    }

    @Test
    void usernameNOTAvailable() {
        //given
        String username = "appuser_username";
        //when
        when(appUserRepository.findByUsername(any())).thenReturn(new AppUser());
        //then
        assertThatThrownBy(() -> underTest.isUsernameAvailable(username))
                .isInstanceOf(UsernameConflictException.class)
                .hasMessageContaining("Username {" + username + "} is unavailable");
    }
    @Test
    void emailAvailable() {
        //given
        String email = "appuser_email";
        //when
        underTest.isEmailAvailable(email);
        //then
        verify(appUserRepository).findByEmail(email);
    }

    @Test
    void emailNotAvailable() {
        //given
        String email = "appuser_email";
        //when
        when(appUserRepository.findByEmail(any())).thenReturn(new AppUser());
        //then
        assertThatThrownBy(() -> underTest.isEmailAvailable(email))
                .isInstanceOf(EmailConflictException.class)
                .hasMessageContaining("Error message for email " + email);
    }

    @Test
    void findByUsername() {
        //given
        String username = "appuser_username";
        //when
        when(appUserRepository.findByUsername(any())).thenReturn(new AppUser());
        underTest.findByUsername(username);
        //then
        verify(appUserRepository).findByUsername(username);
    }

    @Test
    void findByUsernameNonExistingUser() {
        //given
        String username = "appuser_username";
        //when
        when(appUserRepository.findByUsername(any())).thenReturn(null);
        //then
        assertThatThrownBy(() -> underTest.findByUsername(username))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User {" + username + "} is not found");
    }

    @Test
    void findById() {
        //given
        Integer appUserId = 1;
        //when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(new AppUser()));
        underTest.findById(appUserId);
        //then
        verify(appUserRepository).findById(appUserId);
    }

    @Test
    void findByIdNonExistingUser() {
        //given
        Integer appUserId = 1;
        //when
        //then
        assertThatThrownBy(() -> underTest.findById(appUserId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User id:{" + appUserId + "} is not found");
    }

    @Test
    void updateUser() {
        //given
        AppUser appUser = new AppUser(
                "appuser_username",
                "appuser_password",
                "appuser_email"
        );
        //when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(appUser));
        underTest.updateUser(1,appUser);

        //then
        ArgumentCaptor<AppUser> appUserCaptor = ArgumentCaptor.forClass(AppUser.class);

        verify(appUserRepository).save(appUserCaptor.capture());

        AppUser capturedAppUser = appUserCaptor.getValue();
        assertThat(capturedAppUser).isEqualTo(appUser);
    }

    @Test
    void updateUserWithInvalidDetails() {
        //given
        AppUser appUser = new AppUser(
                "appuser_username",
                "appuser_password",
                "appuser_email"
        );
        //when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(appUser));
        appUser.setUsername(null);

        //then
        assertThatThrownBy(() -> underTest.updateUser(1,appUser))
                .isInstanceOf(InvalidAppUserDetailsException.class)
                .hasMessageContaining("Invalid app user details exception");
    }


    @Test
    void deleteUser() {
        //given
        // when
        when(appUserRepository.findById(any())).thenReturn(Optional.of(new AppUser()));
        underTest.deleteUser(1);
        //then
        verify(appUserRepository).deleteById(1);
    }
}