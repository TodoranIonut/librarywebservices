package com.example.authenticationservice.repository;

import com.example.authenticationservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {
    Role findRoleByName(String roleName);
}
