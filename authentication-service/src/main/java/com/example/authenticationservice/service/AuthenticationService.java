package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.requestDTO.AppUserRegisterRequestBody;

import java.util.List;

public interface AuthenticationService {

    List<AppUser> getAppUsers();
    AppUser saveAppUser(AppUserRegisterRequestBody newUser);
    AppUser addRoleToUser(String role, String username);
    boolean isUsernameAvailable(String username);
    boolean isEmailAvailable(String email);
    AppUser getUser(String username);
}
