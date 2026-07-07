package com.example.learnly.service.iml;

import com.example.learnly.dto.lesson.CreateLessonDto;
import com.example.learnly.dto.lesson.UpdateLessonDto;
import com.example.learnly.entity.course.Course;
import com.example.learnly.entity.lesson.Lesson;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.repository.LessonRepository;
import com.example.learnly.service.CourseService;
import com.example.learnly.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final CourseService courseService;

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
        Objects.requireNonNull(courseId, "courseId");
        Objects.requireNonNull(createLessonDto, "createLessonDto");

        Course course = courseService.getById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found course with id " + courseId));

        Lesson lesson = Lesson.builder()
                .title(createLessonDto.title())
                .description(createLessonDto.description())
                .course(course)
                .build();

        return lessonRepository.save(lesson);
    }

    @Override
    public Lesson update(Long id, UpdateLessonDto updateLessonDto) {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(updateLessonDto, "updateLessonDto");

        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found lesson with id " + id));

        if (updateLessonDto.title() != null)
            lesson.setTitle(updateLessonDto.title());

        if (updateLessonDto.description() != null)
            lesson.setDescription(updateLessonDto.description());

        return lessonRepository.save(lesson);
    }

    @Override
    public void deleteById(Long id) {
        Objects.requireNonNull(id, "id");

        lessonRepository.deleteById(id);
    }

}
