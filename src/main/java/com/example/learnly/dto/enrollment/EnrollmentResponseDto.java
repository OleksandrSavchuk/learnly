package com.example.learnly.dto.enrollment;

import java.time.LocalDate;

public record EnrollmentResponseDto(
        Long courseId,
        Long studentId,
        String status,
        LocalDate enrollmentDate,
        LocalDate completionDate,
        Integer progressPercentage
) {
}
