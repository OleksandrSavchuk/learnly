package com.example.learnly.dto.lesson;

public record LessonResponseDto(
        String title,
        String description,
        Long courseId
) {
}
