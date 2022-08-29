package com.example.manageusersservice.requestDTO;

import lombok.Data;

@Data
public class AddRoleToUserRequestBody {
    
    private String username;
    private String roleName;
}
