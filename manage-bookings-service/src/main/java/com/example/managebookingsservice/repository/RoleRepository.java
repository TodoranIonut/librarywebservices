package com.example.managebookingsservice.repository;

import com.example.managebookingsservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByName(String roleName);
}
