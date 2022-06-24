package com.example.authenticationservice.service;

import com.example.authenticationservice.domain.AppUser;
import com.example.authenticationservice.domain.Role;
import com.example.authenticationservice.repository.AppUserRepository;
import com.example.authenticationservice.repository.RoleRepository;
import com.example.authenticationservice.request.AppUserRegisterRequestBody;
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
                null,
                newUser.getUsername(),
                passwordEncoder.encode(newUser.getPassword()),
                newUser.getEmail(),
                true,
                true,
                true,
                true,
                null
                );

        return appUserRepository.save(newAppUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);
        if(appUser == null){
            log.error("user not found in DB");
            throw new UsernameNotFoundException("user not found in DB");
        } else {
            log.info("user found in the DB: {}",username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach( role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(appUser.getUsername(),appUser.getPassword(), authorities);
    }


}
