package com.example.learnly.service;

import com.example.learnly.dto.lesson.CreateLessonDto;
import com.example.learnly.dto.lesson.UpdateLessonDto;
import com.example.learnly.entity.lesson.Lesson;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    List<Lesson> getAllByCourseId(Long courseId);

    Optional<Lesson> getById(Long id);

    Lesson create(Long courseId, CreateLessonDto createLessonDto);

    Lesson update(Long id, UpdateLessonDto updateLessonDto);

    void deleteById(Long id);

}
