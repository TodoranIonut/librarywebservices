package com.example.managebookingsservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Permission {

    private Integer idPermission;
    private String name;

}
