package com.example.authenticationservice.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class AppUserDetails implements UserDetails {


    private String username;
    private String password;
    private Set<? extends GrantedAuthority> grantedAuthorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public AppUserDetails(String username,
                           String password,
                           Set<? extends GrantedAuthority> authorities,
                           boolean isCredentialsNonExpired,
                           boolean isAccountNonLocked,
                           boolean isAccountNonExpired,
                           boolean isEnabled) {
        this.username = username;
        this.password = password;
        this.grantedAuthorities = authorities;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isAccountNonExpired = isAccountNonExpired;
        this.isEnabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
