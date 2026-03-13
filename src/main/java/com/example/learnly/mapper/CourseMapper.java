package com.example.learnly.mapper;

import com.example.learnly.dto.course.CourseResponseDto;
import com.example.learnly.entity.course.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    @Mapping(source = "instructor.id", target = "instructorId")
    CourseResponseDto toResponse(Course course);

    List<CourseResponseDto> toResponse(List<Course> courses);

}
