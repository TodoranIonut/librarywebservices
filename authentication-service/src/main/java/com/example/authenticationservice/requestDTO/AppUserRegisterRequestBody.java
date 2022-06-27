package com.example.authenticationservice.requestDTO;

import lombok.Data;

@Data
public class AppUserRegisterRequestBody {

    private String username;
    private String password;
    private String email;
}