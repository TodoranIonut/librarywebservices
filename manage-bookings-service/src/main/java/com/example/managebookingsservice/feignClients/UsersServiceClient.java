package com.example.managebookingsservice.feignClients;

import com.example.managebookingsservice.domain.AppUser;
import com.example.managebookingsservice.domain.Book;
import com.example.managebookingsservice.requestDTO.AddRoleToUserRequestBody;
import com.example.managebookingsservice.requestDTO.AppUserRegisterRequestBody;
import com.sun.istack.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="manage-users-service", url = "localhost:8083/api/user")
public interface UsersServiceClient {

    @GetMapping("/getAll")
    ResponseEntity<List<AppUser>> getAllUsers();

    @PostMapping("/register")
    ResponseEntity<?> saveUser(@RequestBody @NotNull AppUserRegisterRequestBody request);

    @PostMapping("/add/role")
    ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserRequestBody request);

    @GetMapping("/id/{userId}")
    ResponseEntity<AppUser> getUserById(@PathVariable Integer userId);

    @GetMapping("/username/{username}")
    ResponseEntity<AppUser> getUserByUsername(@PathVariable String username);

    @PostMapping("/update/{idAppUser}")
    ResponseEntity<AppUser> updateUser(@PathVariable @NotNull Integer idAppUser, @RequestBody @NotNull AppUser appUser);

    @DeleteMapping("/delete/{idBook}")
    ResponseEntity<?> deleteUser(@PathVariable @NotNull Integer idBook);

}
