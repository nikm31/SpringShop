package ru.geekbrains.springshop.api.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class AuthResponse {
    private String token;
    private Collection<? extends GrantedAuthority> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }

    public void setRoles(Collection<? extends GrantedAuthority> roles) {
        this.roles = roles;
    }

    public AuthResponse(String token, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.roles = roles;
    }

    public AuthResponse() {
    }
}
