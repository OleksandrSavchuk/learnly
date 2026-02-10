package com.example.learnly.security;

import com.example.learnly.entity.user.User;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleUserDetailsService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with email: " + username));

        return new SimpleUserDetails(user);
    }

    public static class SimpleUserDetails implements UserDetails {

        private static final String SPRING_SECURITY_ROLE_PREFIX = "Role_";

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
    }

}
