package com.example.learnly.security;

import com.example.learnly.entity.user.User;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

}
