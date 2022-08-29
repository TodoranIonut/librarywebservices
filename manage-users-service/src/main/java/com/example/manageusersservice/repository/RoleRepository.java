package com.example.manageusersservice.repository;

import com.example.manageusersservice.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Permission, Integer> {
    Permission findRoleByName(String roleName);
}