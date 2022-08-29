package com.example.managebookingsservice.requestDTO;

import lombok.Data;

@Data
public class AddRoleToUserRequestBody {
    
    private String username;
    private String roleName;
}
