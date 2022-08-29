package com.example.manageusersservice.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRegisterRequestBody {

    private String username;
    private String password;
    private String email;
}
