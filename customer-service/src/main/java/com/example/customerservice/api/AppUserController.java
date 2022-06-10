package com.example.customerservice.api;

import com.domain.AppUser;
import com.example.customerservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<AppUser> getUsers(){
        return userService.getUsers();
    }

}
