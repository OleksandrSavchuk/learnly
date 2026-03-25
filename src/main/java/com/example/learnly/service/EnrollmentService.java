package com.example.learnly.service;

import com.example.learnly.entity.course.Enrollment;

public interface EnrollmentService {

    Enrollment enroll(Long courseId, Long userId);

    Enrollment unenroll(Long courseId, Long userId);

    boolean isEnrolled(Long courseId, Long userId);

}
