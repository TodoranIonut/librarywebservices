package com.example.manageusersservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "role")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPermission;
    private String name;

}
