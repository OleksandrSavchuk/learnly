package com.example.learnly.service.iml;

import com.example.learnly.dto.lesson.CreateLessonDto;
import com.example.learnly.dto.lesson.UpdateLessonDto;
import com.example.learnly.entity.lesson.Lesson;
import com.example.learnly.repository.LessonRepository;
import com.example.learnly.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllByCourseId(Long courseId) {
        return lessonRepository.findByCourseId(courseId);
    }

    @Override
    public Optional<Lesson> getById(Long id) {
        return lessonRepository.findById(id);
    }

    @Override
    public Lesson create(Long courseId, CreateLessonDto createLessonDto) {
        return null;
    }

    @Override
    public Lesson update(Long id, UpdateLessonDto updateLessonDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

}
