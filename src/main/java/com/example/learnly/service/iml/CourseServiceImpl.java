package com.example.learnly.service.iml;

import com.example.learnly.dto.course.CreateCourseDto;
import com.example.learnly.dto.course.UpdateCourseDto;
import com.example.learnly.entity.course.Course;
import com.example.learnly.entity.user.User;
import com.example.learnly.exception.CriticalSystemException;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.repository.CourseRepository;
import com.example.learnly.service.CourseService;
import com.example.learnly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserService userService;

    @Override
    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    @Override
    public Optional<Course> getById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public Course create(CreateCourseDto createCourseDto) {
        User instructor = getCurrentUser();
        Course course = Course.builder()
                .title(createCourseDto.title())
                .description(createCourseDto.description())
                .instructor(instructor)
                .build();
        return courseRepository.save(course);
    }

    @Override
    public Course update(Long id, UpdateCourseDto updateCourseDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found course with id: " + id));

        String title = updateCourseDto.title();
        if (title != null) {
            course.setTitle(title);
        }

        String description = updateCourseDto.description();
        if(description != null) {
            course.setDescription(description);
        }

        return courseRepository.save(course);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public boolean isOwner(Long courseId, Long userId) {
        return courseRepository.existsByIdAndInstructorId(courseId, userId);
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userService.getByEmail(email)
                .orElseThrow(() -> new CriticalSystemException("Not found current user with email: " + email));
    }

}
