package com.example.learnly.mapper;

import com.example.learnly.dto.lesson.LessonResponseDto;
import com.example.learnly.entity.lesson.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(source = "course.id", target = "courseId")
    LessonResponseDto toResponse(Lesson lesson);

    List<LessonResponseDto> toResponse(List<Lesson> lessons);

}
