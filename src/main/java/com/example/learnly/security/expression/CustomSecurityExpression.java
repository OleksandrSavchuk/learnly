package com.example.learnly.security.expression;

import com.example.learnly.entity.user.User;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final UserService userService;

    public boolean canAccessUser(Long id) {
        User user = getCurrentUser();
        return user.getId().equals(id) || user.getRole().getName().equals("ADMIN");
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Unauthenticated");
        }
        String username = authentication.getName();
        return userService.getByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Not found user with email: " + username));
    }

}
