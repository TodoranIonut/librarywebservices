package com.example.manageusersservice.service;

import com.example.manageusersservice.domain.AppUser;
import com.example.manageusersservice.domain.Permission;
import com.example.manageusersservice.repository.AppUserRepository;
import com.example.manageusersservice.repository.RoleRepository;
import com.example.manageusersservice.requestDTO.AppUserRegisterRequestBody;
import com.example.manageusersservice.utils.exceptions.EmailConflictException;
import com.example.manageusersservice.utils.exceptions.InvalidAppUserDetailsException;
import com.example.manageusersservice.utils.exceptions.UserNotFoundException;
import com.example.manageusersservice.utils.exceptions.UsernameConflictException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManageUsersServiceImplementation implements ManageUsersClientService{

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser saveAppUser(AppUserRegisterRequestBody newUser) {

        log.info("save new user");
        isEmailAvailable(newUser.getEmail());
        isUsernameAvailable(newUser.getUsername());
        AppUser newAppUser = new AppUser(
                newUser.getUsername(),
                passwordEncoder.encode(newUser.getPassword()),
                newUser.getEmail());

        return appUserRepository.save(newAppUser);
    }

    @Override
    public AppUser addRoleToUser(String roleName, String username) {
        isUsernameAvailable(username);
        AppUser appUser = appUserRepository.findByUsername(username);
        Permission role = roleRepository.findRoleByName(roleName);
        appUser.getPermissions().add(role);
        return appUser;
    }

    @Override
    public void isUsernameAvailable(String username) {
        if(appUserRepository.findByUsername(username) == null){
            log.info("username is available");
        } else {
            log.error("username is taken");
            throw new UsernameConflictException("Username {" + username + "} is unavailable");
        }
    }

    @Override
    public void isEmailAvailable(String email) {
        if(appUserRepository.findByEmail(email) == null){
            log.info("email is available");
        } else {
            log.error("email is taken");
            throw new EmailConflictException(email);
        }
    }

    @Override
    public AppUser findByUsername(String username) {
        log.info("get app user {} details", username);
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null){
                throw new UserNotFoundException("User {" + username + "} is not found");
        } else {
            return appUser;
        }
    }

    @Override
    public AppUser findById(Integer appUserId){
        log.info("get app user {} details", appUserId);
        AppUser appUser = appUserRepository.findById(appUserId).orElse(null);
        if(appUser == null){
            log.error("user with id {} does not exist ", appUserId);
            throw new UserNotFoundException("User id:{" + appUserId + "} is not found");
        } else {
            return appUser;
        }
    }

    @Override
    public AppUser updateUser(Integer idUser, AppUser newAppUser){
        log.info("update user {}", idUser);
        AppUser appUser = findById(idUser);
        if (appUser != null && appUser.getUsername() != null && appUser.getPassword() != null) {
            appUser.setUsername(newAppUser.getUsername());
            appUser.setPassword(newAppUser.getPassword());
            appUser.setEmail(newAppUser.getEmail());
            appUser.setAccountNonExpired(newAppUser.isAccountNonExpired());
            appUser.setAccountNonLocked(newAppUser.isAccountNonLocked());
            appUser.setCredentialsNonExpired(newAppUser.isCredentialsNonExpired());
            appUser.setEnabled(newAppUser.isEnabled());
            appUserRepository.save(appUser);
            return appUser;
        }
        throw new InvalidAppUserDetailsException();
    }

    @Override
    public void deleteUser(Integer userId) {
        log.info("delete user id {}", userId);
        findById(userId);
        appUserRepository.deleteById(userId);
    }
}
