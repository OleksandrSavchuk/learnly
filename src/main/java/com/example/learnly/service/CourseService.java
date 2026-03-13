package com.example.learnly.service;

import com.example.learnly.dto.course.CreateCourseDto;
import com.example.learnly.dto.course.UpdateCourseDto;
import com.example.learnly.entity.course.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> getAll();

    Optional<Course> getById(Long id);

    Course create(CreateCourseDto createCourseDto);

    Course update(Long id, UpdateCourseDto updateCourseDto);

    void deleteById(Long id);

    boolean isOwner(Long courseId, Long userId);

}
