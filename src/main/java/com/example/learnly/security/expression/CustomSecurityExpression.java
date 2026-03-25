package com.example.learnly.security.expression;

import com.example.learnly.entity.lesson.Lesson;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.security.SimpleUserDetails;
import com.example.learnly.service.CourseService;
import com.example.learnly.service.EnrollmentService;
import com.example.learnly.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("customSecurityExpression")
@RequiredArgsConstructor
public class CustomSecurityExpression {

    private final CourseService courseService;

    private final LessonService lessonService;

    private final EnrollmentService enrollmentService;

    public boolean canAccessUser(Long id) {
        Long userId = getCurrentUserId();
        return userId.equals(id);
    }

    public boolean isCourseOwner(Long courseId) {
        Long userId = getCurrentUserId();
        return courseService.isOwner(courseId, userId);
    }

    public boolean canAccessCourse(Long courseId) {
        Long userId = getCurrentUserId();
        return courseService.isOwner(courseId, userId) || enrollmentService.isEnrolled(courseId, userId);
    }

    public boolean isLessonOwner(Long lessonId) {
        Long userId = getCurrentUserId();
        Lesson lesson = lessonService.getById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found lesson with id: " + lessonId));
        Long courseId = lesson.getCourse().getId();
        return courseService.isOwner(courseId, userId);
    }

    public boolean canAccessLesson(Long lessonId) {
        Long userId = getCurrentUserId();
        Lesson lesson = lessonService.getById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found lesson with id: " + lessonId));
        Long courseId = lesson.getCourse().getId();
        return courseService.isOwner(courseId, userId) || enrollmentService.isEnrolled(courseId, userId);
    }

    private Long getCurrentUserId() {
        SimpleUserDetails user = (SimpleUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return user.getId();
    }

}
