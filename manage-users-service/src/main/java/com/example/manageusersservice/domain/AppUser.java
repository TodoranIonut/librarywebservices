package com.example.manageusersservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idUser;
    private String username;
    private String password;
    private String email;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Permission> permissions;

    public AppUser(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
        permissions = new ArrayList<>();
    }
}