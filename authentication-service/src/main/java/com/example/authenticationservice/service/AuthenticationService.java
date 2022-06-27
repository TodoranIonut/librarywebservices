package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.requestDTO.AppUserRegisterRequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(
        value = "authentication",
        url = "http://localhost:8084/auth"
)
public interface AuthenticationService {

    @GetMapping("/users")
    List<AppUser> getAppUsers();

    AppUser saveAppUser(AppUserRegisterRequestBody newUser);
    AppUser addRoleToUser(String role, String username);
    boolean isUsernameAvailable(String username);
    boolean isEmailAvailable(String email);
    AppUser getUser(String username);
}
