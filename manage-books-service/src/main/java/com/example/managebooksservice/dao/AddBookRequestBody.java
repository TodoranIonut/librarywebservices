package com.example.managebooksservice.dao;

import lombok.Data;

@Data
public class AddBookRequestBody {

    private String title;
    private String author;

}
