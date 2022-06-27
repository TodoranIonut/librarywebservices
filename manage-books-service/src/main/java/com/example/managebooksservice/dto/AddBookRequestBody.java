package com.example.managebooksservice.dto;

import lombok.Data;

@Data
public class AddBookRequestBody {

    private String title;
    private String author;

}
