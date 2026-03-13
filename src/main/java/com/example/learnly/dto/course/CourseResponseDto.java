package com.example.learnly.dto.course;

public record CourseResponseDto(
        Long id,
        String title,
        String description,
        Long instructorId
) {
}
