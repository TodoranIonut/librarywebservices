package com.example.authenticationservice.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.requestDTO.AddRoleToUserRequestBody;
import com.example.authenticationservice.requestDTO.AppUserRegisterRequestBody;
import com.example.authenticationservice.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(authenticationService.getAppUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody @NotNull AppUserRegisterRequestBody form) {

        if (authenticationService.isEmailAvailable(form.getEmail())) {
            if (authenticationService.isUsernameAvailable(form.getUsername())) {
                URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/register").toUriString());
                return ResponseEntity.created(uri).body(authenticationService.saveAppUser(form));
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is taken");
            }
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email is taken");
        }
    }

    @PostMapping("/add/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserRequestBody form) {
        if (authenticationService.isUsernameAvailable(form.getUsername())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid user");
        } else {
            return ResponseEntity.ok().body(authenticationService.addRoleToUser(form.getRoleName(), form.getUsername()));
        }
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {

                String refreshToken = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();

                AppUser appUser = authenticationService.getUser(username);

                String accessToken = JWT.create()
                        .withSubject(appUser.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String,String> tokens = new HashMap<>();
                tokens.put("access_token",accessToken);
                tokens.put("refresh_token",refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


            }catch (Exception exception){

                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String,String> error = new HashMap<>();
                error.put("error_message",exception.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        } else{
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
