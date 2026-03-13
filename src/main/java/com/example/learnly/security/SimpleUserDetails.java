package com.example.learnly.security;

import com.example.learnly.entity.user.User;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class SimpleUserDetails implements UserDetails {

    private static final String SPRING_SECURITY_ROLE_PREFIX = "ROLE_";

    private final User user;

    public SimpleUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roleName = SPRING_SECURITY_ROLE_PREFIX + user.getRole().getName();
        return List.of(new SimpleGrantedAuthority(roleName));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    public Long getId() {
        return user.getId();
    }
}
