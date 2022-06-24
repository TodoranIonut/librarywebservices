package com.example.authenticationservice.controller;

import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.request.AppUserRegisterRequestBody;
import com.example.authenticationservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUserRegisterRequestBody newUser){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register").toUriString());
        return ResponseEntity.created(uri).body(authenticationService.saveAppUser(newUser));
    }

}
