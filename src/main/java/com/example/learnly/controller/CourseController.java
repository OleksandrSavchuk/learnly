package com.example.learnly.controller;

import com.example.learnly.dto.course.CourseResponseDto;
import com.example.learnly.dto.course.CreateCourseDto;
import com.example.learnly.dto.course.UpdateCourseDto;
import com.example.learnly.dto.enrollment.EnrollmentResponseDto;
import com.example.learnly.entity.course.Course;
import com.example.learnly.entity.course.Enrollment;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.mapper.CourseMapper;
import com.example.learnly.mapper.EnrollmentMapper;
import com.example.learnly.security.SimpleUserDetails;
import com.example.learnly.service.CourseService;
import com.example.learnly.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    private final EnrollmentService enrollmentService;

    private final CourseMapper courseMapper;

    private final EnrollmentMapper enrollmentMapper;

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAll() {
        List<Course> courses = courseService.getAll();
        List<CourseResponseDto> courseResponseDtoList = courseMapper.toResponse(courses);
        return ResponseEntity.ok(courseResponseDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getById(@PathVariable Long id) {
        Course course = courseService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found course with id: " + id));
        CourseResponseDto courseResponseDto = courseMapper.toResponse(course);
        return ResponseEntity.ok(courseResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<CourseResponseDto> create(@RequestBody CreateCourseDto createCourseDto) {
        Course course = courseService.create(createCourseDto);
        CourseResponseDto courseResponseDto = courseMapper.toResponse(course);
        return ResponseEntity
                .created(URI.create("/api/courses/" + course.getId()))
                .body(courseResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.canAccessCourse(#id)")
    @PatchMapping("/{id}/update")
    public ResponseEntity<CourseResponseDto> update(@PathVariable Long id,
                                                    @RequestBody UpdateCourseDto updateCourseDto) {
        Course course = courseService.update(id, updateCourseDto);
        CourseResponseDto courseResponseDto = courseMapper.toResponse(course);
        return ResponseEntity.ok(courseResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.canAccessCourse(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{courseId}/enroll")
    public ResponseEntity<EnrollmentResponseDto> enroll(@PathVariable Long courseId,
                                                        @AuthenticationPrincipal SimpleUserDetails simpleUserDetails) {
        Enrollment enrollment = enrollmentService.enroll(courseId, simpleUserDetails.getId());
        EnrollmentResponseDto enrollmentResponseDto = enrollmentMapper.toResponse(enrollment);
        return ResponseEntity.ok(enrollmentResponseDto);
    }

    @PostMapping("/{courseId}/unenroll")
    public ResponseEntity<EnrollmentResponseDto> unenroll(@PathVariable Long courseId,
                                                          @AuthenticationPrincipal SimpleUserDetails simpleUserDetails) {
        Enrollment enrollment = enrollmentService.unenroll(courseId, simpleUserDetails.getId());
        EnrollmentResponseDto enrollmentResponseDto = enrollmentMapper.toResponse(enrollment);
        return ResponseEntity.ok(enrollmentResponseDto);
    }

}
