package com.example.authenticationservice.repository;


import com.example.authenticationservice.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Integer> {
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
}
