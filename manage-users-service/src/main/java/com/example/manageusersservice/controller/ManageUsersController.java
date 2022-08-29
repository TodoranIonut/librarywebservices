package com.example.manageusersservice.controller;

import com.example.manageusersservice.domain.AppUser;
import com.example.manageusersservice.requestDTO.AddRoleToUserRequestBody;
import com.example.manageusersservice.requestDTO.AppUserRegisterRequestBody;
import com.example.manageusersservice.service.ManageUsersClientService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ManageUsersController {

    @Autowired
    @Qualifier("manageUsersServiceImplementation")
    private ManageUsersClientService manageUserService;

    @GetMapping("/all")
    public ResponseEntity<List<AppUser>> getAllUsers() {
        return ResponseEntity.ok().body(manageUserService.getAppUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody @NotNull AppUserRegisterRequestBody request) {
       URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/register").toUriString());
       return ResponseEntity.created(uri).body(manageUserService.saveAppUser(request));
    }

    @PostMapping("/add/role")
    public ResponseEntity<?> addRoleToUser(@RequestBody AddRoleToUserRequestBody request) {
        return ResponseEntity.ok().body(manageUserService.addRoleToUser(request.getRoleName(), request.getUsername()));
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<AppUser> getUserById(@PathVariable Integer userId) {
        return ResponseEntity.ok().body(manageUserService.findById(userId));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AppUser> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok().body(manageUserService.findByUsername(username));
    }

    @PostMapping("/update/{idAppUser}")
    public ResponseEntity<AppUser> updateUser(@PathVariable @NotNull Integer idAppUser, @RequestBody @NotNull AppUser appUser) {
        return ResponseEntity.ok().body(manageUserService.updateUser(idAppUser, appUser));
    }

    @DeleteMapping("/delete/{idBook}")
    public ResponseEntity<?> deleteUser(@PathVariable @NotNull Integer idBook) {
        manageUserService.deleteUser(idBook);
        return ResponseEntity.ok().body("book " + idBook + " deleted");
    }
}
