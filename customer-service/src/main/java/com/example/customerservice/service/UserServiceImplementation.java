package com.example.customerservice.service;

import com.domain.AppUser;
import com.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public List<AppUser> getUsers() {
        return appUserRepository.findAll();
    }
}