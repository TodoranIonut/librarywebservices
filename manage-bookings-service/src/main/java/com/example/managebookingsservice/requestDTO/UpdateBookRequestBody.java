package com.example.managebookingsservice.requestDTO;

import com.example.managebookingsservice.domain.AppUser;
import lombok.Data;

@Data
public class UpdateBookRequestBody {

    private String title;
    private String author;
    private String status;
    private AppUser appUser;
}
