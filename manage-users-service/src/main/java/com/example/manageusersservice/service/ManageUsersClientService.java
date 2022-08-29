package com.example.manageusersservice.service;

import com.example.manageusersservice.domain.AppUser;
import com.example.manageusersservice.requestDTO.AppUserRegisterRequestBody;
import org.springframework.cloud.openfeign.FeignClient;

import java.util.List;

@FeignClient(name = "manage-users-service", url = "localhost:8083")
public interface ManageUsersClientService {

    List<AppUser> getAppUsers();

    AppUser saveAppUser(AppUserRegisterRequestBody newUser);

    AppUser addRoleToUser(String roleName, String username);

    AppUser findByUsername(String username);

    AppUser updateUser(Integer idUser, AppUser newAppUser);

    AppUser findById(Integer userId);

    void deleteUser(Integer userId);

    void isUsernameAvailable(String username);

    void isEmailAvailable(String email);

}
