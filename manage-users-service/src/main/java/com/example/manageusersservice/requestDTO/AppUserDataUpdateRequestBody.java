package com.example.manageusersservice.requestDTO;

import lombok.Data;

@Data
public class AppUserDataUpdateRequestBody {

    private String username;
    private String password;
    private String email;
    private String role;
}