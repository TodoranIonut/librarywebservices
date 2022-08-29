package com.example.managebooksservice.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.managebooksservice.domain.Status.AVAILABLE;

@Data
@Entity
@Table(name = "book")
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idBook;
    private String title;
    private String author;
    private String status = String.valueOf(AVAILABLE);

    @Column(name = "user_id")
    private Integer idUser;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }
}
