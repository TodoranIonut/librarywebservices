package com.example.managebookingsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Integer idBook;
    private String title;
    private String author;
    private String status;

}
