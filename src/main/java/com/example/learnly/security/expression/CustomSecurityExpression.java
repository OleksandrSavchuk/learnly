package com.example.learnly.security.expression;

import com.example.learnly.security.SimpleUserDetails;
import com.example.learnly.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final CourseService courseService;

    public boolean canAccessUser(Long id) {
        Long userId = getCurrentUserId();
        return userId.equals(id);
    }

    public boolean canAccessCourse(Long courseId) {
        Long userId = getCurrentUserId();
        return courseService.isOwner(courseId, userId);
    }

    private Long getCurrentUserId() {
        SimpleUserDetails user = (SimpleUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return user.getId();
    }

}
