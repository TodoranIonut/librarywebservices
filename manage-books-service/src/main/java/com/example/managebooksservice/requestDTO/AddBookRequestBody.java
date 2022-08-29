package com.example.managebooksservice.requestDTO;

import lombok.Data;

@Data
public class AddBookRequestBody {

    private String title;
    private String author;

}
