package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.repository.AppUserRepository;
import com.example.authenticationservice.repository.RoleRepository;
import com.example.authenticationservice.requestDTO.AppUserRegisterRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthenticationServiceImplementation implements AuthenticationService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveAppUser(AppUserRegisterRequestBody newUser) {

        AppUser newAppUser = new AppUser(
                newUser.getUsername(),
                passwordEncoder.encode(newUser.getPassword()),
                newUser.getEmail());

        return appUserRepository.save(newAppUser);
    }

    @Override
    public AppUser addRoleToUser(String roleName, String username) {
        AppUser appUser = appUserRepository.findByUsername(username);
        Role role = roleRepository.findRoleByName(roleName);
        appUser.getRoles().add(role);
        return appUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if (appUser == null) {
            log.error("user not found in DB");
            throw new UsernameNotFoundException("user not found in DB");
        } else {
            log.info("user found in the DB: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        if(appUserRepository.findByUsername(username) == null){
            log.info("username is available");
            return true;
        } else {
            log.error("username is taken");
            return false;
        }
    }

    @Override
    public boolean isEmailAvailable(String email) {
        if(appUserRepository.findByEmail(email) == null){
            log.info("email is available");
            return true;
        } else {
            log.error("email is taken");
            return false;
        }
    }

    @Override
    public AppUser getUser(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAppUsers() {
        return appUserRepository.findAll();
    }
}
