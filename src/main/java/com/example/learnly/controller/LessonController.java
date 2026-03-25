package com.example.learnly.controller;

import com.example.learnly.dto.lesson.LessonResponseDto;
import com.example.learnly.dto.lesson.UpdateLessonDto;
import com.example.learnly.entity.lesson.Lesson;
import com.example.learnly.exception.ResourceNotFoundException;
import com.example.learnly.mapper.LessonMapper;
import com.example.learnly.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    private final LessonMapper lessonMapper;

    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.canAccessLesson(#id)")
    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getById(@PathVariable Long id) {
        Lesson lesson = lessonService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found lesson with id: " + id));
        LessonResponseDto lessonResponseDto = lessonMapper.toResponse(lesson);
        return ResponseEntity.ok(lessonResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.isLessonOwner(#id)")
    @PatchMapping("/{id}")
    public ResponseEntity<LessonResponseDto> update(@PathVariable Long id,
                                                    @RequestBody UpdateLessonDto updateLessonDto) {
        Lesson lesson = lessonService.update(id, updateLessonDto);
        LessonResponseDto lessonResponseDto = lessonMapper.toResponse(lesson);
        return ResponseEntity.ok(lessonResponseDto);
    }

    @PreAuthorize("hasRole('ADMIN') or @customSecurityExpression.isLessonOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        lessonService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
