package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.request.AppUserRegisterRequestBody;

public interface AuthenticationService {

    AppUser saveAppUser(AppUserRegisterRequestBody newUser);

}
