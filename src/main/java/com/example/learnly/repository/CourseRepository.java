package com.example.learnly.repository;

import com.example.learnly.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByIdAndInstructorId(Long courseId, Long userId);
}
