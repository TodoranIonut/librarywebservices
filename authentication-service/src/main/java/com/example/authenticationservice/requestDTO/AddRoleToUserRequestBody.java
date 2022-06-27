package com.example.authenticationservice.requestDTO;

import lombok.Data;

@Data
public class AddRoleToUserRequestBody {

    private String username;
    private String roleName;
}
