package com.example.learnly.repository;

import com.example.learnly.entity.course.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByCourseIdAndStudentId(Long courseId, Long studentId);

}
