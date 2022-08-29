package com.example.managebooksservice.requestDTO;

import lombok.Data;

@Data
public class UpdateBookRequestBody {

    private String title;
    private String author;
    private String status;
    private Integer userId;
}
